package server.managers;

import common.interaction.User;

public class AccessManager {
    private static DatabaseManager databaseManager = DatabaseHandler.getDatabaseManager();

    public static boolean access(User user, String commandName) {
        return databaseManager.confirmUser(user) | commandName.equals("register") | commandName.equals("entry");
    }
}
