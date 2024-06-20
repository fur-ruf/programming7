package server.command;

import common.error.*;
import common.interaction.User;
import common.organization.Organization;
import common.organization.OrganizationType;
import server.managers.CollectionManager;
import server.tools.InteractConsole;

import java.util.Iterator;

public class CommandFilterLessThanType extends Command {
    public CommandFilterLessThanType(CollectionManager collection) {
        super("filter_less_than_type type", "вывод элемента, значение поля type которого меньше заданного", collection);
    }

    @Override
    public String execute(User user, Organization organization, int id, OrganizationType type) throws InvalidArgumentException {
        StringBuilder message = new StringBuilder();
        if (type == null) {
            throw new InvalidArgumentException("Введенный type не найден. Работа команды прервана");
        }
        int max = type.getTax();
        Iterator<Organization> iterator = this.getCollection().getOrganizations().iterator();
        while (iterator.hasNext()) {
            Organization org = iterator.next();
            if (org.getType().getTax() < max) {
                message.append(InteractConsole.printResult("Элемент, у которого type меньше заданного: " + org.toString() + "\n"));
            }
        }
        return message.append(InteractConsole.printMessage("Работа команды завершена")).toString();
    }
}
