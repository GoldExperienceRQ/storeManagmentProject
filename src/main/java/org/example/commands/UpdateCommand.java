package org.example.commands;

import org.example.Message;
import org.example.MySql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateCommand implements Command{
    public void execute(Message message, MySql mySql){
        System.out.println("In update command");
        mySql.update(message);
    }
}
