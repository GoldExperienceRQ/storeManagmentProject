package org.example;

import java.net.InetAddress;
import java.util.Arrays;
import java.net.InetAddress;

public class Sender {

    private Sender(){

    }
    public static void sendMessage(byte[] message, InetAddress target){
        if(message == null || target == null){
            throw new IllegalArgumentException("Message or target cannot be null");
        }
        System.out.println("Sent message: " + Arrays.toString(message) + " to " + target);
    }
}
