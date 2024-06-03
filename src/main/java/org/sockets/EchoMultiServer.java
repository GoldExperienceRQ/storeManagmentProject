package org.sockets;

import java.io.*;
import java.io.IOException;
import java.net.*;

public class EchoMultiServer {
    private ServerSocket serverSocket;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while(true){
            new EchoClientHandler(serverSocket.accept()).start();
        }
    }
    public void stop() throws IOException {
        serverSocket.close();
    }
    public static class EchoClientHandler extends Thread{
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public EchoClientHandler(Socket socket){
            this.clientSocket = socket;
        }

        @Override
        public void run(){
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String msg;
                while((msg = in.readLine()) != null){
                    if(".".equals(msg)){
                        out.println("tschuss");
                        break;
                    }
                    out.println(msg);
                }
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void main(String[] args) throws IOException {
        EchoMultiServer server = new EchoMultiServer();
        server.start(6666);
    }
}
