package org.example.commands;

import org.example.Message;
import org.example.MySql;

public class DeleteCommand implements Command{

    public void execute(Message message, MySql mySql) {
        System.out.println("Delete command executed");
        mySql.delete(message);
    }
}
