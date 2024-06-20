package client.command;

import common.error.*;

public abstract class ClientCommand {
    private String name;
    private String description;
    private CommandRequestStatus status;


    public ClientCommand(String name, String description, CommandRequestStatus status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public abstract CommandResult execute(String args) throws InvalidArgumentException, IllegalRecursionException, ErrorInFileException, FileNotExistException, CommandNotExistException, CantHaveScannerException;

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public CommandRequestStatus getStatus() {
        return status;
    }
}
