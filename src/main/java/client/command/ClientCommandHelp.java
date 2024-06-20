package client.command;

import client.managers.ClientCommandManager;
import common.error.*;
import common.tools.*;

/**
 * Реализовывает команду вывода информации о командах, доступных пользователю
 * @author Polik
 */
public class ClientCommandHelp extends ClientCommand {
    private ClientCommandManager commandManager;
    public ClientCommandHelp(ClientCommandManager commandManager) {
        super("help", "справка по доступным командам", CommandRequestStatus.ONLY_CLIENT_COMMAND);
        this.commandManager = commandManager;
    }
    @Override
    public CommandResult execute(String args) throws InvalidArgumentException {
        if (!args.isEmpty()) {
            throw new InvalidArgumentException("Введены неверные аргументы");
        }
        InteractConsole.printMessage("Доступные команды:");
        for (ClientCommand command: commandManager.getCommands().values())
            InteractConsole.printSuccess(command.getName() + " : " + command.getDescription());
        return new CommandResult(this.getName());
    }
}
