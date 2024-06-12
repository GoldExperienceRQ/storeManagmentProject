package org.example;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;

public class Encryptor{


    public static byte[] encryptMessage(Message message) {
        ByteBuffer buffer = ByteBuffer.allocate(20 + message.getText().length());
        buffer.putInt(message.getcType());
        buffer.putInt(message.getbUserId());
        buffer.putInt(message.getProduct_id());
        buffer.putInt(message.getPrice());
        buffer.putInt(message.getQuantity());
        buffer.put(message.getText().getBytes());


        return cipher(buffer.array());
    }

    private static byte[] cipher(byte[] responseBuffer)  {
        SecretKeySpec secretKey = new SecretKeySpec(Message.getKey(), "AES");
        try{
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(responseBuffer);
        }catch(Exception e){throw new RuntimeException(e);}

    }
}



