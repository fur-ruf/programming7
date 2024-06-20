package common.error;

import common.IOHandler.FileManager;

import java.io.IOException;

public class ErrorInFileException extends IOException {
    public ErrorInFileException() {
        super("В файле " + FileManager.getFileOnTop().getName() + " ошибка");
    }
}
