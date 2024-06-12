package org.example.sockets;


import org.example.*;

import java.io.*;
import java.net.*;
import java.nio.channels.InterruptedByTimeoutException;


public class StoreClientTCP {
    private Socket clientSocket;
    private DataOutputStream out;
    private DataInputStream in;
    private int port;

    public void startConnection(String ip, int port) throws IOException {
        this.port = port;
        clientSocket = new Socket(ip, this.port);
        out = new DataOutputStream(clientSocket.getOutputStream());
        in = new DataInputStream(clientSocket.getInputStream());
    }

    public void endConnection(StoreClientTCP client) {

    }

    public byte[] sendMessage(Message msg) throws IOException {
        while (true) {
            try {
                byte[] req = Encryptor.encryptMessage(msg);
                out.writeInt(req.length);
                out.write(req);

                int length = in.readInt();
                byte[] res = new byte[length];
                in.readFully(res);
                return res;
            } catch (IOException e) {
                System.out.println("Server is down, retrying connection...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                startConnection("127.0.0.1", this.port);
            }


        }
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public static void main(String[] args) throws IOException {
        StoreClientTCP client = new StoreClientTCP();
        client.startConnection("127.0.0.1", 6666);
        byte[] response = client.sendMessage(new Message.Builder(1, 4, "Delete")
                .product_id(7)
                .build());

        byte[] response2 = client.sendMessage(new Message.Builder(2, 2, "Read voda")
                .product_id(6)
                .build());

        client.sendMessage(new Message.Builder(1, 0, "end").build());
        Message message = Decryptor.decryptMessage(response);
        Message message2 = Decryptor.decryptMessage(response2);
        System.out.println(message.getText());
        System.out.println(message2.getText());

    }
}
