package server.managers;

import common.interaction.User;
import common.organization.Organization;
import common.error.RepeatIdException;

import java.util.*;

public class CollectionManager {
    private String name = "Безымянная";
    private String type = "common/organization";
    private HashSet<Organization> organizations;
    private HashSet<Integer> ids = new HashSet<Integer>();
    private Date creationDate;
    private Date lastChange;
    private Date lastSave;

    public CollectionManager(){
        organizations = new HashSet<Organization>();
        creationDate = new Date();
        lastSave = new Date();
        lastChange = new Date();
    }
    public CollectionManager(String name){
        organizations = new HashSet<Organization>();
        creationDate = new Date();
        lastSave = new Date();
        lastChange = new Date();
        this.name = name;
    }
    public void addOrganization(User user, Organization organization) {
        lastChange = new Date();
        DatabaseHandler.getDatabaseManager().addObject(organization, user);
        organization.setUserLogin(user.name());
        organizations.add(organization);
    }

    public void deleteOrganization(User user, int id) {
        lastChange = new Date();
        DatabaseHandler.getDatabaseManager().deleteObject(id, user);
    }

    public HashSet<Organization> getOrganizations() {
        return organizations;
    }

    public void clear(User user) {
        ArrayList<Integer> deletedIds = new ArrayList<>();
        Iterator<Organization> iterator = this.getOrganizations().iterator();
        while (iterator.hasNext()) {
            Organization org = iterator.next();
            int id = org.getId();
            if (user.name().equals(org.getUserLogin())) {
                deletedIds.add(id);
                this.popID(id);
                iterator.remove();
            }
        }
        DatabaseHandler.getDatabaseManager().deleteAllObjects(user, deletedIds);
        lastChange = new Date();
    }

    public boolean isEmpty() {
        return organizations.isEmpty();
    }
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public String toString() {
        String output = "Информация о коллекции: имя - " + name + ", тип хранимых данных - " + type + ", размер коллекции - " +
                organizations.size() + ", время создания - " + creationDate + ", время последнего изменения - " + lastChange +
                ", время последнего сохранения - " + lastSave;
        return output;
    }

    public void addID(Integer id) throws RepeatIdException{
        boolean isAdd = ids.add(id);
        if (!isAdd) {
            throw new RepeatIdException("Повтор ID!");
        }
    }

    public void popID(Integer id) {
        ids.remove(id);
    }

    public int getBiggestID() {
        int value = 1;
        Iterator<Integer> iterator = ids.iterator();
        int max = -1;
        while (iterator.hasNext()) {
            value = iterator.next();
            if (max < value) {
                max = value;
            }
        }
        return value;
    }
    public int getRightID() {
        return organizations.stream().filter(Objects::nonNull)
                .map(Organization::getId)
                .mapToInt(Integer::intValue)
                .max().orElse(0) + 1;
    }
    public void changeLastSave() {
        lastSave = new Date();
    }
}
