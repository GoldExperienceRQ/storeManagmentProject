package org.example;

import java.net.UnknownHostException;

public class Processor{

    private enum MessageType {
        END_PROCESS,
        COUNT_PRODUCT,
        SUBTRACT_PRODUCT,
        ADD_PRODUCT,
        ADD_GROUP,
        ADD_NAME_TO_GROUP,
        SET_PRICE,
    }


    public static Message process(Message message)  {
        String responseText = "Response to " + MessageType.values()[message.getcType()].name() + " " + message.getText() + ": ok";

        return new Message(message.getcType(), message.getbUserId(), responseText);
    }

}
