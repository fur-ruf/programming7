package common.error;

import java.io.IOException;

public class ExitException extends IOException {
    public ExitException(String message) {
        super(message);
    }
}
