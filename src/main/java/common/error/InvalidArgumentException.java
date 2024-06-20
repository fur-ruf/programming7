package common.error;

import java.io.IOException;

public class InvalidArgumentException extends IOException {
    public InvalidArgumentException(String message) {
        super(message);
    }
}
