package org.example;

import org.example.commands.*;

import java.net.UnknownHostException;

public class Processor{

    private enum MessageType {
        END_PROCESS,
        CREATE_PRODUCT,
        READ_PRODUCT,
        UPDATE_PRODUCT,
        DELETE_PRODUCT,
    }


    public static Message process(Message message)  {
        System.out.println("In processor");
        if(message.getcType() > 0 && message.getcType() < MessageType.values().length){
            Command command = CommandSelector.selectCommand(message.getcType());
            assert command != null;
            MySql mySql = new MySql();
            mySql.initialize("127.0.0.1:3306/store_schema", "root", "my_sql");
            command.execute(message, mySql);
        }
        else if(message.getcType() == MessageType.END_PROCESS.ordinal()){
            return new Message.Builder(message.getbUserId(), message.getcType(), "End process").build();
        }
        else{
            return new Message.Builder(message.getbUserId(), message.getcType(),"Unknown command").build();
        }
        String responseText = "Response to " + MessageType.values()[message.getcType()].name() + " " + message.getText() + ": ok";
        return new Message.Builder(message.getbUserId(), message.getcType(), responseText).build();
    }

}
