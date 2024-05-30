package org.example;

public class Receiver{
    private static int messageNum = 1;


    public Receiver() {
    }

    public void receiveMessage(byte[] message) {
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }
        System.out.println("Running: Message " + messageNum);
        messageNum++;
        Thread decryption = new Thread(new Decryptor(message));
        decryption.start();
    }

}
