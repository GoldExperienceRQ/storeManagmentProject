package org.example.sockets;

import org.example.Decryptor;
import org.example.Encryptor;
import org.example.Message;
import org.example.Processor;

import java.io.IOException;
import java.net.*;

public class StoreServerUDP {
    private byte[] buf = new byte[256];

    public void start(int port){
        System.out.println("UDP Server is running on port " + port);
        try(DatagramSocket serverSocket = new DatagramSocket(port)){

            while(true){

                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                serverSocket.receive(packet);

                new UDPPacketHandler(packet, serverSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class UDPPacketHandler extends Thread{
        DatagramPacket packet;
        DatagramSocket serverSocket;

        public UDPPacketHandler(DatagramPacket packet, DatagramSocket serverSocket){
            this.packet = packet;
            this.serverSocket = serverSocket;
        }

        @Override
        public void run(){
            System.out.println("Processing packet..");
            InetAddress clientAddress = packet.getAddress();
            int clientPort = packet.getPort();
            byte[] received = new byte[packet.getLength()];

            System.arraycopy(packet.getData(), packet.getOffset(), received, 0, packet.getLength());

            Message message = Decryptor.decryptMessage(received);
            System.out.println(message.getText());
            Message responseMessage = Processor.process(message);
            System.out.println(responseMessage.getText());
            byte[] res = Encryptor.encryptMessage(responseMessage);
            DatagramPacket sendPacket = new DatagramPacket(res, res.length, clientAddress, clientPort);
            try {
                serverSocket.send(sendPacket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
