package server.commandForConnection;

import common.interaction.User;
import common.organization.Organization;
import common.organization.OrganizationType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.command.Command;
import server.managers.DatabaseHandler;
import server.managers.DatabaseManager;

public class CommandEntry extends Command {
    private static final Logger serverLogger = LogManager.getLogger("entry");
    public CommandEntry(){
        super("entry", "выполняет функцию входа в аккаунт");
    }

    @Override
    public String execute(User user, Organization organization, int id, OrganizationType type) {
        DatabaseManager databaseManager = DatabaseHandler.getDatabaseManager();
        if (databaseManager.confirmUser(user)) {
            serverLogger.info("yes");
            return "Доступ разрешён";
        }
        serverLogger.info("no");
        return "Введённые пароль и логин не являются актуальными или пользователь не зарегистрирован ;(";
    }
}
