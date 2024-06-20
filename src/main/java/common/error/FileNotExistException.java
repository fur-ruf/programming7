package common.error;

import java.io.FileNotFoundException;

public class FileNotExistException extends FileNotFoundException {
    public FileNotExistException(String message) {
        super(message);
    }
}
