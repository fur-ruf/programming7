package server.command;

import common.error.*;

import common.interaction.User;
import common.organization.Organization;
import common.organization.OrganizationType;
import server.managers.CollectionManager;
import server.tools.InteractConsole;


public class CommandInfo extends Command {
    public CommandInfo(CollectionManager collection) {
        super("info", "вывод информации о коллекции", collection);
    }

    @Override
    public String execute(User user, Organization organization, int id, OrganizationType type) throws InvalidArgumentException {
        return "Информация о коллекции:\n" +
                InteractConsole.printSuccess(this.getCollection().toString());
    }
}
