package client.command;

import common.error.*;

public class ClientCommandMaxById extends ClientCommand {
    public ClientCommandMaxById() {
        super("max_by_id", "вывод любого объекта из коллекции, значение поля id которого является максимальным", CommandRequestStatus.COMMON);
    }

    @Override
    public CommandResult execute(String args) throws InvalidArgumentException {
        if (!args.isEmpty()) {
            throw new InvalidArgumentException("Введены неверные аргументы");
        }
        return new CommandResult(this.getName());
    }
}
