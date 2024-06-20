package server.command;

import common.error.*;
import common.interaction.User;
import common.organization.Organization;
import common.organization.OrganizationType;
import server.managers.CollectionManager;
import server.tools.InteractConsole;


public class CommandClear extends Command{
    public CommandClear(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию", collectionManager);
    }

    @Override
    public String execute(User user, Organization organization, int id, OrganizationType type) throws InvalidArgumentException {
        this.getCollection().clear(user);
        return InteractConsole.printSuccess("Коллекция очищена!");
    }
}
