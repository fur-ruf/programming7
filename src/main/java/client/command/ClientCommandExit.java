package client.command;

import common.error.*;

public class ClientCommandExit extends ClientCommand {
    public ClientCommandExit() {
        super("exit", "завершить программу (без сохранения в файл)", CommandRequestStatus.EXIT);
    }

    @Override
    public CommandResult execute(String args) throws InvalidArgumentException {
        if (!args.isEmpty()) {
            throw new InvalidArgumentException("Введены неверные аргументы");
        }
        return new CommandResult(this.getName());
    }
}
