package org.example.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.nio.Buffer;

public class StoreServerTCP {
    ServerSocket serverSocket;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while(true){
            new ClientSocketHandler(serverSocket.accept()).start();
        }
    }
    public void close() throws IOException {
        serverSocket.close();
    }
    public static class ClientSocketHandler extends Thread{

        Socket clientSocket;
        PrintWriter out;
        BufferedReader in;

        ClientSocketHandler(Socket socket){
            clientSocket = socket;
        }
        @Override
        public void run(){

            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                while((inputLine = in.readLine()) != null){
                    if(inputLine.equals(".")){
                        out.println(inputLine);
                        out.println("Good Bye");
                    }
                    out.println(inputLine);
                }
                in.close();
                out.close();
                clientSocket.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
