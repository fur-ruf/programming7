package client.command;

import common.error.*;
import common.organization.*;
import server.tools.InteractConsole;

public class ClientCommandFilterLessThanType extends ClientCommand {
    public ClientCommandFilterLessThanType() {
        super("filter_less_than_type", "вывод элемента, значение поля type которого меньше заданного", CommandRequestStatus.COMMON);
    }

    @Override
    public CommandResult execute(String args) throws InvalidArgumentException {
        String arg = args.toUpperCase().trim();
        OrganizationType type;
        try {
            type = OrganizationType.valueOf(arg);
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException(arg + "Введенный type не найден. Работа команды прервана");
        }
        return new CommandResult(this.getName(), type);
    }
}
