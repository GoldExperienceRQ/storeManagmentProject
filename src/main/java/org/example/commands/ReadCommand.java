package org.example.commands;

import org.example.Message;
import org.example.MySql;

import java.sql.*;


public class ReadCommand implements Command{
    public void execute(Message message, MySql mySql){
        System.out.println("In read command");
        mySql.read(message);
    }

}
