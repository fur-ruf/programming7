package client.command;

import common.error.*;

public class ClientCommandRemoveById extends ClientCommand{
    public ClientCommandRemoveById() {
        super("remove_by_id", "удаление элемента из коллекции по его id", CommandRequestStatus.COMMON);
    }

    @Override
    public CommandResult execute(String args) throws InvalidArgumentException{
        int id = 0;
        try {
            id = Integer.parseInt(args.trim());
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException("Ошибка в ходе выполнения команды " + getName() + " неверный id");
        }
        return new CommandResult(this.getName(), id);
    }
}
