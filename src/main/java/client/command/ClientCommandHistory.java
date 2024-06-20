package client.command;

import client.managers.ClientCommandManager;
import common.error.*;
import common.tools.InteractConsole;

/**
 * Реализовывает команду вывода истории действий пользователя
 * @author Polik
 */
public class ClientCommandHistory extends ClientCommand{
    private ClientCommandManager commandManager;
    public ClientCommandHistory(ClientCommandManager commandManager) {
        super("history", "вывод последних 6 команд (без их аргументов)", CommandRequestStatus.ONLY_CLIENT_COMMAND);
        this.commandManager = commandManager;
    }

    @Override
    public CommandResult execute(String args) throws InvalidArgumentException {
        if (!args.isEmpty()) {
            throw new InvalidArgumentException("Введены неверные аргументы");
        }
        InteractConsole.printMessage("История ваших действий: ");
        InteractConsole.printSuccess(String.join(" ", commandManager.getCommandHistory()));
        return new CommandResult(this.getName());
    }
}
