package server.command;

import common.error.*;
import common.interaction.User;
import common.organization.Organization;
import common.organization.OrganizationType;
import server.managers.CollectionManager;
import server.tools.InteractConsole;

import java.util.Iterator;

public class CommandRemoveById extends Command{
    public CommandRemoveById(CollectionManager collection) {
        super("remove_by_id id", "удаление элемента из коллекции по его id", collection);
    }

    @Override
    public String execute(User user, Organization organization, int id, OrganizationType type) throws InvalidArgumentException{
        if (id == -1) {
            throw new InvalidArgumentException("Неверное ID!");
        }
        Iterator<Organization> iterator = this.getCollection().getOrganizations().iterator();
        while (iterator.hasNext()) {
            Organization org = iterator.next();
            if (org.getId() == id && org.getUserLogin().equals(user.name())) {
                this.getCollection().popID(id);
                this.getCollection().deleteOrganization(user, id);
                iterator.remove();
                return "Организация успешно удалена!";
            }
        }
        return "Организация не удалена! Введенное ID не найдено или элемент вам не принадлежит";
    }
}
