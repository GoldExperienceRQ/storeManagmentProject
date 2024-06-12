package org.example.commands;

import org.example.Message;
import org.example.MySql;

public interface Command {
    void execute(Message message, MySql mySql);
}
