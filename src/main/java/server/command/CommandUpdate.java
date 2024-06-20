package server.command;

import common.error.*;
import common.interaction.User;
import common.organization.Organization;
import common.organization.OrganizationType;
import server.managers.CollectionManager;
import server.tools.InteractConsole;

import java.util.Iterator;

public class CommandUpdate extends Command{
    public CommandUpdate(CollectionManager collection) {
        super("update id", "обновление значениея элемента коллекции, id которого равен заданному", collection);
    }

    @Override
    public String execute(User user, Organization organization, int id, OrganizationType type) throws InvalidArgumentException{
        if (id == -1 | organization == null) {
            throw new InvalidArgumentException("Неверные аргументы");
        }
        boolean idNotExist = true;
        Iterator<Organization> iterator = this.getCollection().getOrganizations().iterator();
        while (iterator.hasNext()) {
            Organization org = iterator.next();
            if ((org.getId() == id) && org.getUserLogin().equals(user.name())) {
                iterator.remove();
                idNotExist = false;
            }
            if ((org.getId() == id) && !org.getUserLogin().equals(user.name())) {
                throw new InvalidArgumentException("Ошибка в ходе выполнения команды " + getName() + ", покушение на чужую собственность");
            }
        }
        if (idNotExist) {
            throw new InvalidArgumentException("Ошибка в ходе выполнения команды " + getName() + ", неверный id");
        }
        this.getCollection().addOrganization(user, organization);
        return "Организация успешно обновлена!";
    }
}
