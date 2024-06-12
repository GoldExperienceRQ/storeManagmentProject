package org.example.sockets;


import org.example.*;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;
import java.util.Arrays;

public class StoreClientUDP {
    private InetAddress serverAddress;
    private int BUFFER_SIZE = 256;
    private int ACK_BUFFER_SIZE = 16;
    public byte[] sendMessage(Message message, int port) throws SocketException {
        try(DatagramSocket clientSocket = new DatagramSocket()){
            serverAddress = InetAddress.getByName("localhost");
            byte[] sendBuffer = Encryptor.encryptMessage(message);
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, port);

            while(true) {
                clientSocket.send(sendPacket);
                clientSocket.setSoTimeout(1000);
                try {
                    byte[] receiveBuffer = new byte[BUFFER_SIZE];
                    DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    clientSocket.receive(receivePacket);
                    byte[] received = new byte[receivePacket.getLength()];
                    System.arraycopy(receivePacket.getData(), receivePacket.getOffset(), received, 0, receivePacket.getLength());
                    return received;
                } catch (SocketTimeoutException e) {
                    System.out.println("Resending message...");
                }
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
//        StoreClientUDP client1 = new StoreClientUDP();
//        byte[] res1 = client1.sendMessage(new Message(1,1,"Hrechka"), 7777);
//        byte[] res2 = client1.sendMessage(new Message(2,2,"Voda"), 7777);
//        System.out.println(Decryptor.decryptMessage(res1).getText());
//        System.out.println(Decryptor.decryptMessage(res2).getText());
//
//        StoreClientUDP client2 = new StoreClientUDP();
//        byte[] res3 = client2.sendMessage(new Message(3,3,"Kartoplia"), 7777);
//        byte[] res4 = client2.sendMessage(new Message(4,4,"Miaso"), 7777);
//        System.out.println(Decryptor.decryptMessage(res3).getText());
//        System.out.println(Decryptor.decryptMessage(res4).getText());
    }
}
