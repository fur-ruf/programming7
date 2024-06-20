package common.error;

import java.io.IOException;

public class XMLErrorException extends IOException {
    public XMLErrorException(String message) {
        super(message);
    }
}

