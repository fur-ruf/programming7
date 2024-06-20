package client.command;

import common.error.*;

public class ClientCommandClear extends ClientCommand {
    public ClientCommandClear() {
        super("clear", "очистить коллекцию", CommandRequestStatus.COMMON);
    }

    @Override
    public CommandResult execute(String args) throws InvalidArgumentException {
        if (!args.isEmpty()) {
            throw new InvalidArgumentException("Введены неверные аргументы");
        }
        return new CommandResult(this.getName());
    }
}
