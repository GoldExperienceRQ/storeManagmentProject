package org.example;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;

public class Decryptor{

    private Decryptor(){

    }

    public static Message decryptMessage(byte[] req) {

        byte[] decipheredRequest = decipher(req);

        ByteBuffer buffer = ByteBuffer.wrap(decipheredRequest);
        int cType = buffer.getInt();
        int bUserId = buffer.getInt();
        int product_id = buffer.getInt();
        int price = buffer.getInt();
        int quantity = buffer.getInt();
        byte[] textInBytes = new byte[buffer.remaining()];
        buffer.get(textInBytes);

        String text = new String(textInBytes);

        return new Message.Builder(bUserId, cType, text).product_id(product_id).price(price).quantity(quantity).build();

    }

    private static byte[] decipher(byte[] cypheredMessage)  {
        SecretKeySpec secretKey = new SecretKeySpec(Message.getKey(), "AES");
        try{
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(cypheredMessage);
        }catch(Exception e){throw new RuntimeException(e);}
    }
}
