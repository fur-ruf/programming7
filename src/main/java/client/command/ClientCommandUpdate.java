package client.command;

import common.error.*;
import client.forms.OrganizationValidator;
import common.organization.*;

public class ClientCommandUpdate extends ClientCommand{
    public ClientCommandUpdate() {
        super("update", "обновление значения элемента коллекции, id которого равен заданному", CommandRequestStatus.COMMON);
    }

    @Override
    public CommandResult execute(String args) throws InvalidArgumentException, ErrorInFileException, CantHaveScannerException {
        int id = 0;
        try {
            id = Integer.parseInt(args.trim());
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException("Ошибка в ходе выполнения команды " + getName() + " неверный id");
        }
        Organization organization = new OrganizationValidator().build();
        organization.setId(id);
        return new CommandResult(this.getName(), organization, id);
    }
}
