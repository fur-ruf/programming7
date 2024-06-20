package client.command;

import common.error.*;
import client.forms.OrganizationValidator;
import common.organization.*;

public class ClientCommandRemoveGreater extends ClientCommand {
    public ClientCommandRemoveGreater() {
        super("remove_greater", "удаление из коллекции всех элементов, превышающих заданный", CommandRequestStatus.COMMON);
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
