package server.commandForConnection;

import common.error.InvalidArgumentException;
import common.interaction.User;
import common.organization.Organization;
import common.organization.OrganizationType;
import server.command.Command;
import server.managers.CollectionManager;
import server.managers.DatabaseHandler;
import server.managers.DatabaseManager;
import server.tools.InteractConsole;

import java.sql.SQLException;
import java.util.Iterator;

public class CommandRegister extends Command {

    public CommandRegister() {
        super("register", "удаление из коллекции всех элементов, превышающих заданный");
    }

    @Override
    public String execute(User user, Organization organization, int id, OrganizationType type) {
        DatabaseManager databaseManager = DatabaseHandler.getDatabaseManager();
        try {
            databaseManager.addUser(user);
        } catch (SQLException e) {
            return "Введённые пароль и логин не являются актуальными или пользователь не зарегистрирован ;(";
        }
        return "Доступ разрешён";
    }
}
