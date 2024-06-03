package org.example;

import org.example.sockets.StoreServerTCP;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.HashMap;

public class App {


    private static byte[] encode(Message m){
        ByteBuffer buffer = ByteBuffer.allocate(8 + m.getText().length());
        buffer.putInt(m.getcType());
        buffer.putInt(m.getbUserId());
        buffer.put(m.getText().getBytes());

        return cipher(buffer.array());
    }

    private static byte[] cipher(byte[] mess){
        SecretKeySpec secretKey = new SecretKeySpec(Message.getKey(), "AES");
        try{
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(mess);
        }catch(Exception e){throw new RuntimeException(e);}
    }
    public static void main(String[] args) throws InterruptedException, IOException {


    }
}
