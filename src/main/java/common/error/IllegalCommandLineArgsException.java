package common.error;

import java.io.IOException;

public class IllegalCommandLineArgsException extends IOException {
    public IllegalCommandLineArgsException(String message) {
        super(message);
    }
}
