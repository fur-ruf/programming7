package common.error;

import java.io.IOException;

public class ErrorInLoginException extends IOException {
    public ErrorInLoginException(String message) {
        super(message);
    }
}
