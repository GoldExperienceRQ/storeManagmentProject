package org.example.commands;

import org.example.Message;
import org.example.MySql;

import java.sql.*;

public class CreateCommand implements Command{
    public void execute(Message message, MySql mySql){
        System.out.println("In create command");
        mySql.create(message);
    }
}
