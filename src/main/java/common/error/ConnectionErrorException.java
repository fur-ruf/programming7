package common.error;

import java.io.IOException;

public class ConnectionErrorException extends IOException {
    public ConnectionErrorException(String message) {
        super(message);
    }
}