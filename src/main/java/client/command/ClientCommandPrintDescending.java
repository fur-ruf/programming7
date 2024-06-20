package client.command;

import common.error.*;

public class ClientCommandPrintDescending extends ClientCommand {
    public ClientCommandPrintDescending() {
        super("print_descending", "вывод элементов коллекции в порядке убывания", CommandRequestStatus.COMMON);
    }

    public CommandResult execute(String args) throws InvalidArgumentException {
        if (!args.isEmpty()) {
            throw new InvalidArgumentException("Введены неверные аргументы");
        }
        return new CommandResult(this.getName());
    }
    // https://stackoverflow.com/questions/3705275/help-comparing-float-member-variables-using-comparators
}
