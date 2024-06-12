package org.example.commands;

public class CommandSelector {

    public static Command selectCommand(int commandType) {
        switch (commandType) {
            case 1:
                return new CreateCommand();
            case 2:
                return new ReadCommand();
            case 3:
                return new UpdateCommand();
            case 4:
                return new DeleteCommand();
            default:
                return null;
        }
    }
}
