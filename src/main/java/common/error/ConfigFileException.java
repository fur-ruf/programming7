package common.error;

import java.io.IOException;

public class ConfigFileException extends IOException  {
    public ConfigFileException(String message) {
        super(message);
    }
}
