package common.IOHandler;

import common.error.FileNotExistException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Reader {
    public static String readFromFile(String path) throws FileNotExistException {
        File file = FileManager.createFile(path);
        try {
            Scanner scannerForReading = new Scanner(file);
            StringBuilder stringBuilder = new StringBuilder();
            while (scannerForReading.hasNext()) {
                stringBuilder.append(scannerForReading.nextLine());
            }
            if (stringBuilder.isEmpty()) {
                return "<set></set>";
            }
            scannerForReading.close();
            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            throw new FileNotExistException("Указанный файл не существует");
        }
    }
}
