package org.example;

public class Receiver{
    private static int messageNum = 1;


    public Receiver() {
    }

    public void receiveMessage(byte[] message) {
        System.out.println("Running: Message " + messageNum);
        messageNum++;
        Thread decryption = new Thread(new Decryptor(message));
        decryption.start();
    }

}
