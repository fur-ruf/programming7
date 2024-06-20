package common.error;

import java.io.IOException;

public class CommandRuntimeException extends IOException {
    public CommandRuntimeException(String message) {
        super(message);
    }
}
