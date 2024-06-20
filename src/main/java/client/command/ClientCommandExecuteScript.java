package client.command;


import common.error.*;

public class ClientCommandExecuteScript extends ClientCommand {

    public ClientCommandExecuteScript() {
        super("execute_script file_name", "считать и исполнить скрипт из указанного файла", CommandRequestStatus.EXECUTE_COMMAND);
    }

    @Override
    public CommandResult execute(String args) throws InvalidArgumentException {
        if (args.isEmpty()) {
            throw new InvalidArgumentException("Введены неверные аргументы");
        }
        return new CommandResult(this.getName(), args);
    }
}
