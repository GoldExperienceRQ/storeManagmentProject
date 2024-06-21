package http;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.sun.net.httpserver.*;
import http.dao.GoodDao;
import http.entities.Good;
import http.entities.TokenResponse;
import http.entities.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class WebServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(8080), 0);


        Sql.initialize("127.0.0.1:3306/store_schema", "root", "my_sql");

        HttpContext login = server.createContext("/login", new LoginHandler());
        HttpContext good = server.createContext("/api/goods", new GoodsHandler());
        good.setAuthenticator(new Auth());

        server.setExecutor(null);
        server.start();
    }

    static class Auth extends Authenticator {
        private static final String SECRET = "my_secret_key";
        private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);

        @Override
        public Result authenticate(HttpExchange httpExchange) {
            var jwtToken = httpExchange.getRequestHeaders().getFirst("Authorization");

            if (jwtToken == null || !isValidJWT(jwtToken)) {
                return new Failure(401);
            }

            // Get data for HttpPrincipal from actual JWT token
            DecodedJWT jwt = JWT.decode(jwtToken);
            String subject = jwt.getSubject();

            return new Success(new HttpPrincipal(subject, ""));
        }

        private static boolean isValidJWT(String jwtToken) {
            try {
                JWTVerifier verifier = JWT.require(ALGORITHM).build();
                verifier.verify(jwtToken);
                return true;
            } catch (JWTVerificationException exception) {
                return false;
            }
        }
    }

    static class LoginHandler implements HttpHandler {

        private static final String SECRET = "my_secret_key";
        private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            ObjectMapper objectMapper = new ObjectMapper();

            if ("POST".equals(exchange.getRequestMethod())) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = br.readLine()) != null) {
                    sb.append(line.trim());
                }

                User user = objectMapper.readValue(sb.toString(), User.class);

                if(user.getLogin().equals("valentyn") && user.getPassword().equals("123")){
                    String token = JWT.create()
                            .withSubject(user.getLogin())
                            .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))
                            .sign(ALGORITHM);
                    String response = objectMapper.writeValueAsString(new TokenResponse(token));
                    exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                    os.close();
                }
                else{
                    String response = "Invalid login or password";
                    exchange.sendResponseHeaders(401, response.getBytes(StandardCharsets.UTF_8).length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                    os.close();
                }
                System.out.println(user.getLogin());
                br.close();
                isr.close();


            }
        }
    }

    static class GoodsHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line = null;

            GoodDao goodDao = new GoodDao();
            ObjectMapper objectMapper = new ObjectMapper();
            if ("GET".equals(exchange.getRequestMethod())) {

                URI requestURI = exchange.getRequestURI();
                String path = requestURI.getPath();
                String[] parts = path.split("/");

                if (parts.length > 3) {
                    String id = parts[3];
                    Good good = goodDao.read(Integer.parseInt(id));
                    if (good == null) {
                        String response = "No such good";
                        exchange.sendResponseHeaders(404, response.getBytes(StandardCharsets.UTF_8).length);
                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes(StandardCharsets.UTF_8));
                        exchange.close();
                    } else {
                        String response = objectMapper.writeValueAsString(good);
                        exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes(StandardCharsets.UTF_8));
                        os.close();
                    }
                } else {
                    List<Good> goods = goodDao.readAll();
                    if (goods.isEmpty()) {
                        String response = "No goods in the store";
                        exchange.sendResponseHeaders(404, response.getBytes(StandardCharsets.UTF_8).length);
                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes(StandardCharsets.UTF_8));
                        exchange.close();
                    }
                    String response = "Goods in total: " + goods.size();
                    exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                    exchange.close();
                }

            } else if ("PUT".equals(exchange.getRequestMethod())) {
                System.out.println("PUT");


                while ((line = br.readLine()) != null) {
                    sb.append(line.trim());
                }
                br.close();
                isr.close();
                System.out.println(sb.toString());

                Good good = null;

                try {
                    good = objectMapper.readValue(sb.toString(), Good.class);
                } catch (Exception e) {
                    String response = "Invalid request body format";
                    exchange.sendResponseHeaders(409, response.getBytes(StandardCharsets.UTF_8).length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                    exchange.close();
                }

                try {
                    goodDao.create(good);
                } catch (SQLDataException e) {
                    String response = e.getMessage();
                    exchange.sendResponseHeaders(409, response.getBytes(StandardCharsets.UTF_8).length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                    exchange.close();
                }


                String response = "Good Added";
                exchange.sendResponseHeaders(201, response.getBytes(StandardCharsets.UTF_8).length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes(StandardCharsets.UTF_8));
                os.close();

            } else if ("POST".equals(exchange.getRequestMethod())) {
                System.out.println("POST");


                URI requestURI = exchange.getRequestURI();
                String path = requestURI.getPath();
                String[] parts = path.split("/");
                String id = parts[3];

                if (goodDao.read(Integer.parseInt(id)) == null) {
                    String response = "No such good";
                    exchange.sendResponseHeaders(404, response.getBytes(StandardCharsets.UTF_8).length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                    exchange.close();
                }

                sb = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    sb.append(line.trim());
                }
                if (sb.toString().contains("id")) {
                    String response = "Id cannot be updated";
                    exchange.sendResponseHeaders(409, response.getBytes(StandardCharsets.UTF_8).length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                    exchange.close();
                }
                // add id to sb
                sb.insert(1, "\"id\":" + id + ",");

                System.out.println(sb.toString());

                Good good = null;

                try {
                    good = objectMapper.readValue(sb.toString(), Good.class);
                } catch (Exception e) {
                    String response = "Invalid request body format";
                    exchange.sendResponseHeaders(409, response.getBytes(StandardCharsets.UTF_8).length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                    exchange.close();
                }
                System.out.println(good);
                try {
                    goodDao.update(good);
                } catch (SQLDataException e) {
                    String response = e.getMessage();
                    exchange.sendResponseHeaders(409, response.getBytes(StandardCharsets.UTF_8).length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                    exchange.close();
                }

                String response = "No Content";
                exchange.sendResponseHeaders(204, response.getBytes(StandardCharsets.UTF_8).length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes(StandardCharsets.UTF_8));
                os.close();
            } else if ("DELETE".equals(exchange.getRequestMethod())) {
                System.out.println("DELETE");

                URI requestURI = exchange.getRequestURI();
                String path = requestURI.getPath();
                String[] parts = path.split("/");
                String id = parts[3];

                if (goodDao.read(Integer.parseInt(id)) == null) {
                    String response = "No such good";
                    exchange.sendResponseHeaders(404, response.getBytes(StandardCharsets.UTF_8).length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                    exchange.close();
                }

                goodDao.delete(Integer.parseInt(id));

                String response = "No Content";
                exchange.sendResponseHeaders(204, response.getBytes(StandardCharsets.UTF_8).length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes(StandardCharsets.UTF_8));
                os.close();
            }
            isr.close();
            br.close();

        }
    }
}
