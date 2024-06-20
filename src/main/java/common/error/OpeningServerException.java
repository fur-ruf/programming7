package common.error;

import java.io.IOException;

public class OpeningServerException extends IOException {
    public OpeningServerException(String message) {
        super(message);
    }
}
