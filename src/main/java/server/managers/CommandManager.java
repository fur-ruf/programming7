package server.managers;

import common.error.InvalidArgumentException;
import common.interaction.Request;
import common.interaction.Response;
import server.command.*;
import server.commandForConnection.CommandEntry;
import server.commandForConnection.CommandRegister;
import server.tools.InteractConsole;

import java.util.HashMap;

public class CommandManager {
    private HashMap<String, Command> commands = new HashMap<>();
    public CommandManager(CollectionManager collection) {
        {
            addCommand("info", new CommandInfo(collection));
            addCommand("show", new CommandShow(collection));
            addCommand("add", new CommandAdd(collection));
            addCommand("update", new CommandUpdate(collection));
            addCommand("remove_by_id", new CommandRemoveById(collection));
            addCommand("clear", new CommandClear(collection));
            addCommand("add_if_min", new CommandAddIfMin(collection));
            addCommand("remove_greater", new CommandRemoveGreater(collection));
            addCommand("max_by_id", new CommandMaxById(collection));
            addCommand("filter_less_than_type", new CommandFilterLessThanType(collection));
            addCommand("print_descending", new CommandPrintDescending(collection));
            addCommand("entry", new CommandEntry());
            addCommand("register", new CommandRegister());
        }
    }

    public void addCommand(String help, Command command) {
        commands.put(help, command);
    }

    public Response execute(Request request) {
        Command command = commands.get(request.getCommandName());
        if (command == null) {
            return new Response("Неизвестная команда");
        }
        try {
            return new Response(command.execute(request.getUser(), request.getOrganization(), request.getId(), request.getType()));
        } catch (InvalidArgumentException e) {
            return new Response(InteractConsole.printError(e.getMessage()));
        }
    }
}
