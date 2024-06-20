package server.command;

import common.error.InvalidArgumentException;
import common.interaction.User;
import common.organization.Organization;
import common.organization.OrganizationType;
import server.managers.CollectionManager;
import server.managers.DatabaseManager;

public abstract class Command {
    private String name;
    private String description;
    private CollectionManager collectionManager;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Command(String name, String description, DatabaseManager databaseManager) {
        this.name = name;
        this.description = description;
    }

    public Command(String name, String description, CollectionManager collectionManager) {
        this.name = name;
        this.description = description;
        this.collectionManager = collectionManager;
    }

    public abstract String execute(User user, Organization organization, int id, OrganizationType type) throws InvalidArgumentException;

    public CollectionManager getCollection() {
        return collectionManager;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

}
