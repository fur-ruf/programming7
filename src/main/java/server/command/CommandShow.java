package server.command;

import common.error.*;
import common.interaction.User;
import common.organization.Organization;
import common.organization.OrganizationType;
import server.managers.CollectionManager;
import server.tools.InteractConsole;

public class CommandShow extends Command{
    public CommandShow(CollectionManager collectionManager) {
        super("show", "вывод всех элементов коллекции в строковом представлении", collectionManager);
    }
    @Override
    public String execute(User user, Organization organization, int id, OrganizationType type) throws InvalidArgumentException {
        StringBuilder message = new StringBuilder();
        message.append("Элементы коллекции:\n");
        for (Organization org: this.getCollection().getOrganizations()) {
            message.append(InteractConsole.printSuccess(org.toString())).append("\n");
        }
        return message.append(InteractConsole.printMessage("Операция успешно выполнена!")).toString();
    }
}
