package org.example.sockets;


import org.example.*;

import java.io.*;
import java.net.*;


public class StoreClientTCP {
    private Socket clientSocket;
    private DataOutputStream out;
    private DataInputStream in;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new DataOutputStream(clientSocket.getOutputStream());
        in = new DataInputStream(clientSocket.getInputStream());
    }
    public void endConnection(StoreClientTCP client){

    }
    public byte[] sendMessage(Message msg) throws IOException {
        byte[] req = Encryptor.encryptMessage(msg);
        out.writeInt(req.length);
        out.write(req);

        int length = in.readInt();
        byte[] res = new byte[length];
        in.readFully(res);
        return res;
    }
    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
    public static  void main(String[] args) throws IOException {
        StoreClientTCP client = new StoreClientTCP();
        client.startConnection("127.0.0.1", 6666);
        byte[] response = client.sendMessage(new Message(1,2, "Hrechka"));
        byte[] response2 = client.sendMessage(new Message(1,2, "Voda"));
        client.sendMessage(new Message(0,0, "end"));
        Message message = Decryptor.decryptMessage(response);
        Message message2 = Decryptor.decryptMessage(response2);

        System.out.println(message.getText());
        System.out.println(message2.getText());
    }
}
