package client.managers;

import client.command.*;
import common.error.*;
import common.interaction.Request;

import java.util.ArrayDeque;
import java.util.HashMap;

public class ClientCommandManager {
    private HashMap<String, ClientCommand> commands = new HashMap<>();
    private ArrayDeque<String> commandHistory = new ArrayDeque<>();

    public ClientCommandManager() {
        {
            addCommand("help", new ClientCommandHelp(this));
            addCommand("info", new ClientCommandInfo());
            addCommand("show", new ClientCommandShow());
            addCommand("add", new ClientCommandAdd());
            addCommand("update", new ClientCommandUpdate());
            addCommand("remove_by_id", new ClientCommandRemoveById());
            addCommand("clear", new ClientCommandClear());
            addCommand("execute_script", new ClientCommandExecuteScript());
            addCommand("exit", new ClientCommandExit());
            addCommand("add_if_min", new ClientCommandAddIfMin());
            addCommand("remove_greater", new ClientCommandRemoveGreater());
            addCommand("history", new ClientCommandHistory(this));
            addCommand("max_by_id", new ClientCommandMaxById());
            addCommand("filter_less_than_type", new ClientCommandFilterLessThanType());
            addCommand("print_descending", new ClientCommandPrintDescending());
        }
    }

    public void addCommand(String help, ClientCommand command) {
        commands.put(help, command);
    }

    public void addToHistory(String command) {
        if (commandHistory.size() == 7) {
            commandHistory.pollFirst();
        }
        commandHistory.add(command);
    }

    public ArrayDeque<String> getCommandHistory() {
        return commandHistory;
    }

    public HashMap<String, ClientCommand> getCommands() {
        return commands;
    }

    public Request execute(String commandName, String arg) throws CommandNotExistException, InvalidArgumentException, IllegalRecursionException, ErrorInFileException, FileNotExistException, CantHaveScannerException {
        ClientCommand command = commands.get(commandName);
        if (command == null) throw new CommandNotExistException("Введенная команда не существует");
        addToHistory(commandName + " " + arg);
        CommandResult resultOfCommand = command.execute(arg);
        return new Request(resultOfCommand.getCommandName(), resultOfCommand.getNewOrganization(), resultOfCommand.getId(), resultOfCommand.getType(), resultOfCommand.getPath(), command.getStatus());
    }
}
