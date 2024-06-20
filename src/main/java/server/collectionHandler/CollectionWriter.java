package server.collectionHandler;

import common.IOHandler.Writer;
import com.thoughtworks.xstream.XStream;
import common.error.FileNotExistException;
import server.managers.CollectionManager;

import java.io.*;

public class CollectionWriter {
    private final XStream xStream = new XStream();
    CollectionManager collectionManager;
    public CollectionWriter(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    public void saveObjects(File file) throws FileNotExistException {
        collectionManager.changeLastSave();
        Writer.write(file, this.xStream.toXML(collectionManager.getOrganizations()));
    }
}
