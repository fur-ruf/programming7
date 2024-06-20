package client.command;

import common.organization.Organization;
import common.organization.OrganizationType;

public class CommandResult {
    private String commandName;
    private Organization newOrganization;
    private Integer id = -1;
    private OrganizationType type;
    private String path;

    public CommandResult(String commandName, Organization newOrganization, Integer id, OrganizationType type) {
        this.commandName = commandName;
        this.newOrganization = newOrganization;
        this.id = id;
        this.type = type;
    }

    public CommandResult(String commandName, Organization newOrganization, Integer id) {
        this.commandName = commandName;
        this.newOrganization = newOrganization;
        this.id = id;
    }

    public CommandResult(String commandName, Integer id) {
        this.commandName = commandName;
        this.id = id;
    }

    public CommandResult(String commandName, String path) {
        this.commandName = commandName;
        this.path = path;
    }

    public CommandResult(String commandName, OrganizationType type) {
        this.commandName = commandName;
        this.type = type;
    }

    public CommandResult(String commandName, Organization newOrganization) {
        this.commandName = commandName;
        this.newOrganization = newOrganization;
    }

    public CommandResult(String commandName) {
        this.commandName = commandName;
    }

    public Integer getId() {
        return id;
    }

    public Organization getNewOrganization() {
        return newOrganization;
    }

    public OrganizationType getType() {
        return type;
    }

    public String getPath() {
        return path;
    }

    public String getCommandName() {
        return commandName;
    }
}
