package common.error;

import java.io.IOException;

public class ErrorInPasswordException extends IOException {
    public ErrorInPasswordException(String message) {
        super(message);
    }
}
