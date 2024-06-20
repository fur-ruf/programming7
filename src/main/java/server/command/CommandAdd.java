package server.command;

import common.error.*;
import common.interaction.User;
import common.organization.Organization;
import common.organization.OrganizationType;
import server.managers.CollectionManager;
import server.tools.InteractConsole;

public class CommandAdd extends Command{
    public CommandAdd(CollectionManager collection){
        super("add", "добавление нового элемента в коллекцию. При введении 'stop' выполнение будет прервано", collection);
    }

    @Override
    public String execute(User user, Organization organization, int id, OrganizationType type) throws InvalidArgumentException {
        if (organization == null) {
            throw new InvalidArgumentException("Введены неверные аргументы");
        }
        System.out.println(user.name());
        organization.setId(getCollection().getRightID());
        this.getCollection().addOrganization(user, organization);
        return "Организация успешно добавлена!";
    }
}
