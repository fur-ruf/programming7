package common.IOHandler;

import common.error.FileNotExistException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    public static void write(File file, String text) throws FileNotExistException {
        try {
            java.io.Writer writer = new FileWriter(file);
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            throw new FileNotExistException("Файл не найден");
        }
    }
}
