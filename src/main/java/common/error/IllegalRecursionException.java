package common.error;

import java.io.IOException;

public class IllegalRecursionException extends IOException {
    public IllegalRecursionException(String message) {
        super(message);
    }
}
