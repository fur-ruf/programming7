package common.input;

import common.error.CantHaveScannerException;
import common.IOHandler.FileManager;

import java.util.Scanner;

public class FileReader extends Input{
    private static Scanner scanner;
    private FileManager fileManager = new FileManager();

    public String nextLine() throws CantHaveScannerException {
        scanner = fileManager.getScanner();
        return scanner.nextLine();
    }
}
