package client.command;

import common.error.*;
import client.forms.OrganizationValidator;
import common.organization.*;

public class ClientCommandAddIfMin extends ClientCommand {
    public ClientCommandAddIfMin() {
        super("add_if_min", "добавление нового элемента в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции", CommandRequestStatus.COMMON);
    }

    public CommandResult execute(String args) throws ErrorInFileException, InvalidArgumentException, CantHaveScannerException {
        if (!args.isEmpty()) {
            throw new InvalidArgumentException("Введены неверные аргументы");
        }
        Organization organization = new OrganizationValidator().build();
        return new CommandResult(this.getName(), organization);
    }
}
