package org.example;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class Encryptor implements Runnable{

    Message response;

    public Encryptor(Message response) {
        this.response = response;
    }
    public void encryptMessage() throws UnknownHostException {
        ByteBuffer buffer = ByteBuffer.allocate(8 + response.getText().length());
        buffer.putInt(response.getcType());
        buffer.putInt(response.getbUserId());
        buffer.put(response.getText().getBytes());

        byte[] encryptedResponse = cipher(buffer.array());

        System.out.println("Response was encrypted. Sending...");

        Sender.sendMessage(encryptedResponse, Inet4Address.getLocalHost());
    }

    private byte[] cipher(byte[] responseBuffer)  {
        SecretKeySpec secretKey = new SecretKeySpec(Message.getKey(), "AES");
        try{
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(responseBuffer);
        }catch(Exception e){throw new RuntimeException(e);}

    }
    @Override
    public void run() {
        try {
            encryptMessage();
        } catch (UnknownHostException  e) {
            throw new RuntimeException(e);
        }
    }
}



