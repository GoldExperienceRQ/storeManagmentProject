package org.example;

import org.example.sockets.StoreServerTCP;
import org.example.sockets.StoreServerUDP;

import java.io.IOException;

public class Receiver{

    public Receiver() {
    }

    public void receiveMessage() throws IOException {

//            server.start(6666);
//            System.out.println("Socket is closed");

        StoreServerUDP serverUDP = new StoreServerUDP();
        serverUDP.start(7777);
    }

}
