package server.command;

import common.error.*;
import common.interaction.User;
import common.organization.Organization;
import common.organization.OrganizationType;
import server.managers.CollectionManager;
import server.tools.InteractConsole;

import java.util.Iterator;

public class CommandMaxById extends Command {
    public CommandMaxById(CollectionManager collectionManager) {
        super("max_by_id", "вывод любого объекта из коллекции, значение поля id которого является максимальным", collectionManager);
    }

    @Override
    public String execute(User user, Organization organization, int id, OrganizationType type) throws InvalidArgumentException {
        if (this.getCollection().isEmpty()) {
            return InteractConsole.printMessage("В коллекции нет элементов");
        } else {
            Iterator<Organization> iterator = this.getCollection().getOrganizations().iterator();
            int max = -1;
            Organization org = null;
            while (iterator.hasNext()) {
                Organization element = iterator.next();
                if (element.getId() > max) {
                    max = element.getId();
                    org = element;
                }
            }
            return InteractConsole.printMessage("Организация с наибодьшим id:\n") + InteractConsole.printSuccess(org.toString());
        }
    }
}
