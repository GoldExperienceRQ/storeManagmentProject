package org.example.sockets;

import org.example.*;

import java.io.*;
import java.net.*;


public class StoreServerTCP {
    ServerSocket serverSocket;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Waiting for a message");
        System.out.println(Thread.currentThread().getName());
        while(true){
            new ClientSocketHandler(serverSocket.accept()).start();
        }
    }
    public void close() throws IOException {
        serverSocket.close();
    }
    public static class ClientSocketHandler extends Thread{

        Socket clientSocket;
        DataOutputStream out;
        DataInputStream in;

        ClientSocketHandler(Socket socket){
            clientSocket = socket;
        }
        @Override
        public void run(){
            System.out.println("Processing the message... ");
            System.out.println(Thread.currentThread().getName());
            try {
                out = new DataOutputStream(clientSocket.getOutputStream());
                in = new DataInputStream(clientSocket.getInputStream());

                int length;
                while((length = in.readInt()) != -1) {
                    System.out.println();
                    System.out.println("Circle one");
                    byte[] req = new byte[length];
                    in.readFully(req);

                    Message message = Decryptor.decryptMessage(req);
                    Message responseMessage = Processor.process(message);
                    System.out.println(responseMessage.getText());
                    byte[] res = Encryptor.encryptMessage(responseMessage);


                    out.writeInt(res.length);
                    out.write(res);
                    System.out.println("Everything is written");
                    if(message.getText().equals("end")){
                        break;
                    }
                }
                System.out.println("Closing the connection");
                in.close();
                out.close();
                clientSocket.close();
                System.out.println("Done");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
