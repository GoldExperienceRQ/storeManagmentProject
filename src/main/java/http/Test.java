package http;

import com.fasterxml.jackson.databind.ObjectMapper;
import http.entities.Good;

public class Test {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = """
                        {
                            "id": 1,
                            "name": "Bread",
                            "price": 1.5,
                            "quantity": 100
                        }
                        """;
        try {
            Good good = objectMapper.readValue(json, Good.class);
            System.out.println(good);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
