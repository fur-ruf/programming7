package common.error;

import java.io.IOException;

public class RepeatIdException extends IOException {
    public RepeatIdException(String message) {
        super(message);
    }
}
