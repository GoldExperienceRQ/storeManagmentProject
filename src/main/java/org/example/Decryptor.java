package org.example;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;

public class Decryptor implements Runnable{

    private final byte[] message;

    public Decryptor(byte[] message){
        this.message = message;
    }

    public void decryptMessage() {

        byte[] decipheredMessage = decipher(message);

        ByteBuffer buffer = ByteBuffer.wrap(decipheredMessage);
        int cType = buffer.getInt();
        int bUserId = buffer.getInt();
        byte[] textInBytes = new byte[buffer.remaining()];
        buffer.get(textInBytes);

        String text = new String(textInBytes);

        System.out.println("Decoded message: " + text);

        Message request = new Message(cType, bUserId, text);

        Thread processing = new Thread(new Processor(request));
        processing.start();

    }

    private byte[] decipher(byte[] cypheredMessage)  {
        SecretKeySpec secretKey = new SecretKeySpec(Message.getKey(), "AES");
        try{
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(cypheredMessage);
        }catch(Exception e){throw new RuntimeException(e);}
    }

    @Override
    public void run() {
        decryptMessage();
    }
}
