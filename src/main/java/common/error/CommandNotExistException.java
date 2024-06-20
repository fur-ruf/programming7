package common.error;

import java.io.IOException;

public class CommandNotExistException extends IOException {
    public CommandNotExistException(String message) {
        super(message);
    }
}