package client.command;

import common.error.*;
import client.forms.OrganizationValidator;
import common.organization.*;

public class ClientCommandAdd extends ClientCommand {
    public ClientCommandAdd(){
        super("add", "добавление нового элемента в коллекцию.", CommandRequestStatus.COMMON);
    }

    @Override
    public CommandResult execute(String args) throws ErrorInFileException, InvalidArgumentException, CantHaveScannerException {
        if (!args.isEmpty()) {
            throw new InvalidArgumentException("Введены неверные аргументы");
        }
        Organization organization = new OrganizationValidator().build();
        return new CommandResult(this.getName(), organization);
    }
}
