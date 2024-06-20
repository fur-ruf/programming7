package client.command;

import common.error.*;

public class ClientCommandShow extends ClientCommand{
    public ClientCommandShow() {
        super("show", "вывод всех элементов коллекции в строковом представлении", CommandRequestStatus.COMMON);
    }
    @Override
    public CommandResult execute(String args) throws InvalidArgumentException {
        if (!args.isEmpty()) {
            throw new InvalidArgumentException("Введены неверные аргументы");
        }
        return new CommandResult(this.getName());
    }
}

