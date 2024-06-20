package common.tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class GettingProperty {
    private Properties properties = new Properties();
    public void makeProperty() {
        properties.setProperty("port", "8083");
        properties.setProperty("host", "127.0.0.1");
        properties.setProperty("reconnectionTimeout", "5000");
        properties.setProperty("maxReconnectionAttempts", "5");
        properties.setProperty("maxReconnectionAttempts", "5");
        properties.setProperty("path", "/Users/amoremore/Desktop/java7/src/main/java/server/fileForSave.xml");
        try {
            FileOutputStream out = new FileOutputStream("/Users/amoremore/Desktop/java7/src/main/resources/application.properties");
            properties.store(out, "");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getProperty(String value) {
        try {
            FileInputStream in = new FileInputStream("/Users/amoremore/Desktop/java7/src/main/resources/application.properties");
            properties.load(in);
            return properties.getProperty(value);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
