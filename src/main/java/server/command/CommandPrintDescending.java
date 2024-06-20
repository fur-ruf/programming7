package server.command;

import common.error.*;
import common.interaction.User;
import common.organization.Organization;
import common.organization.OrganizationType;
import server.managers.CollectionManager;
import server.tools.InteractConsole;

import java.util.*;

public class CommandPrintDescending extends Command {
    public CommandPrintDescending(CollectionManager collection) {
        super("print_descending", "вывод элементов коллекции в порядке убывания", collection);
    }

    public String execute(User user, Organization organization, int id, OrganizationType type) throws InvalidArgumentException {
        StringBuilder message = new StringBuilder();
        message.append(InteractConsole.printMessage("Элементы коллекции в порядке убывания:\n"));
        ArrayList<Organization> sortedList = new ArrayList<>(this.getCollection().getOrganizations());
        sortedList.sort(Collections.reverseOrder(Organization.COMPARE_BY_SIZE));
        for (Organization org: sortedList) {
            message.append(InteractConsole.printSuccess(org.toString() + " " + org.getOrganizationSize())).append("\n");
        }
        return message.append(InteractConsole.printMessage("Операция выполнена!")).toString();
    }
    // https://stackoverflow.com/questions/3705275/help-comparing-float-member-variables-using-comparators
}
