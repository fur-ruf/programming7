package client.command;

import common.error.*;

public class ClientCommandInfo extends ClientCommand {
    public ClientCommandInfo() {
        super("info", "вывод информации о коллекции", CommandRequestStatus.COMMON);
    }
    @Override
    public CommandResult execute(String args) throws InvalidArgumentException {
        if (!args.isEmpty()) {
            throw new InvalidArgumentException("Введены неверные аргументы");
        }
        return new CommandResult(this.getName());
    }
}
