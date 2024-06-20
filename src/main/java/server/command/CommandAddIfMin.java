package server.command;

import common.error.*;
import common.interaction.User;
import common.organization.Organization;
import common.organization.OrganizationType;
import server.managers.CollectionManager;

import java.util.Iterator;

public class CommandAddIfMin extends Command {
    public CommandAddIfMin(CollectionManager collection) {
        super("add_if_min", "добавление нового элемента в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции", collection);
    }

    public String execute(User user, Organization organization, int id, OrganizationType type) throws InvalidArgumentException {
        String message = "";
        if (organization == null) {
            throw new InvalidArgumentException("Введены неверные аргументы");
        }
        organization.setId(getCollection().getRightID());
        if (this.getCollection().isEmpty()) {
            this.getCollection().addOrganization(user, organization);
            message += "Организация добавлена!\n";
        } else {
            Iterator<Organization> iterator = this.getCollection().getOrganizations().iterator();
            float min = iterator.next().getOrganizationSize();
            while (iterator.hasNext()) {
                Organization org = iterator.next();
                if (min > org.getOrganizationSize()) {
                    min = org.getOrganizationSize();
                }
            }
            if (min > organization.getOrganizationSize()) {
                this.getCollection().addOrganization(user, organization);
                message += "Организация добавлена!\n";
            }
        }
        return message + "Операция успешно выполнена!";
    }
}
