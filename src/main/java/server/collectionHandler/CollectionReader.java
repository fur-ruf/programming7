package server.collectionHandler;

import common.IOHandler.Reader;
import common.interaction.User;
import common.organization.Organization;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.security.AnyTypePermission;
import common.error.FileNotExistException;
import common.error.RepeatIdException;
import common.error.XMLErrorException;
import server.managers.CollectionManager;

import com.thoughtworks.xstream.XStream;

import java.util.HashSet;
import java.util.Iterator;


public class CollectionReader {
    private final CollectionManager collectionManager;
    public CollectionReader(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public void getCollection(String path) throws FileNotExistException, RepeatIdException, XMLErrorException {
        XStream xstream = new XStream();
        xstream.alias("Organization", Organization.class);
        xstream.alias("Set", CollectionManager.class);
        xstream.addPermission(AnyTypePermission.ANY);
        xstream.addImplicitCollection(CollectionManager.class, "organizations");
        HashSet collectionManagerWithObjects;
        try {
            collectionManagerWithObjects = (HashSet) xstream.fromXML(Reader.readFromFile(path));
        }  catch (XStreamException | ClassCastException e) {
            throw new XMLErrorException("Замечена ошибка в XML");
        }
        Iterator<Organization> iterator = collectionManagerWithObjects.iterator();
        while (iterator.hasNext()) {
            Organization org = iterator.next();
            this.collectionManager.addID(org.getId());
            this.collectionManager.addOrganization(new User(org.getUserLogin(), ""), org);
        }
        Organization.updateId(collectionManager.getBiggestID());
    }
}

