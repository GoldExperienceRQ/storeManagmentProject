package org.example;

import org.example.sockets.StoreServerTCP;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;

public class App {


    public static void main(String[] args) throws IOException {
        Receiver receiver = new Receiver();
        receiver.receiveMessage();


    }
}
