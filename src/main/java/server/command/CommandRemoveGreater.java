package server.command;

import common.error.*;
import common.interaction.User;
import common.organization.Organization;
import common.organization.OrganizationType;
import server.managers.CollectionManager;
import server.tools.InteractConsole;

import java.util.Iterator;

public class CommandRemoveGreater extends Command {
    public CommandRemoveGreater(CollectionManager collection) {
        super("remove_greater", "удаление из коллекции всех элементов, превышающих заданный", collection);
    }

    @Override
    public String execute(User user, Organization organization, int id, OrganizationType type) throws InvalidArgumentException {
        if (organization == null) {
            throw new InvalidArgumentException("Введены неверные аргументы");
        }
        organization.setId(getCollection().getRightID());
        StringBuilder message = new StringBuilder();
        Iterator<Organization> iterator = this.getCollection().getOrganizations().iterator();
        while (iterator.hasNext()) {
            Organization org = iterator.next();
            if (org.getOrganizationSize() > organization.getOrganizationSize() && org.getUserLogin().equals(user.name())) {
                this.getCollection().popID(org.getId());
                iterator.remove();
                this.getCollection().deleteOrganization(user, org.getId());
                message.append(InteractConsole.printSuccess("Организация " + org +  " удалена\n"));
            }
        }
        return message.append(InteractConsole.printMessage("Операция успешно выполнена!")).toString();
    }
}
