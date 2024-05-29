package org.example;

import java.net.InetAddress;
import java.util.Arrays;
import java.net.InetAddress;

public class Sender {

    public static void sendMessage(byte[] message, InetAddress target){
        System.out.println("Sent message: " + Arrays.toString(message) + " to " + target);
    }
}
