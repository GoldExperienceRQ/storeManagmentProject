package org.sockets;

import java.io.*;
import java.net.*;

public class GreetClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }
    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        String resp = in.readLine();
        return resp;
    }
    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
    public static void main(String[] args) throws IOException {
        GreetClient client = new GreetClient();
        client.startConnection("127.1.1.0", 6666);
        String message = client.sendMessage("Hello");
        System.out.println(message);
        String message2 = client.sendMessage("World");
        System.out.println(message2);
        String message3 = client.sendMessage(".");
        System.out.println(message3);


    }
}
