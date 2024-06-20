package common.interaction;

import client.command.CommandRequestStatus;
import common.organization.Organization;
import common.organization.OrganizationType;

import java.io.Serializable;

public class Request implements Serializable {
    private String commandName;
    private Organization organization;
    private Integer id;
    private OrganizationType type;
    private CommandRequestStatus status;
    private String path;
    private User user;

    public Request(String commandName, Organization newOrganization, Integer id, OrganizationType type, String path, CommandRequestStatus status) {
        this.commandName = commandName;
        this.organization = newOrganization;
        this.id = id;
        this.type = type;
        this.status = status;
        this.path = path;
    }

    public Request(String commandName, User user) {
        this.commandName = commandName;
        this.user = user;
    }

    public String getCommandName() {
        return commandName;
    }

    public Integer getId() {
        if (id == null) {
            return -1;
        }
        return id;
    }

    public Organization getOrganization() {
        return organization;
    }

    public OrganizationType getType() {
        return type;
    }

    public CommandRequestStatus getStatus() {
        return status;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public void setStatus(CommandRequestStatus status) {
        this.status = status;
    }

    public void setType(OrganizationType type) {
        this.type = type;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPath() {
        return path;
    }

    public User getUser() {
        return user;
    }
}
