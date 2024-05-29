package org.example;

import java.net.UnknownHostException;

public class Processor implements Runnable {

    private enum MessageType {
        COUNT_PRODUCT,
        SUBTRACT_PRODUCT,
        ADD_PRODUCT,
        ADD_GROUP,
        ADD_NAME_TO_GROUP,
        SET_PRICE,
    }

    private final Message request;

    public Processor(Message request) {
        this.request = request;
    }

    public void process()  {
        String responseText = "Response to " + MessageType.values()[request.getcType()].name() + ": ok";
        Message response = new Message(request.getcType(), request.getbUserId(), responseText);

        System.out.println("Response text: " + response.getText());

        Thread encryption = new Thread(new Encryptor(response));
        encryption.start();

    }

    @Override
    public void run() {
        process();
    }
}
