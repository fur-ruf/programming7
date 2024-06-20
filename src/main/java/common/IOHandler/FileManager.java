package common.IOHandler;

import common.error.CantHaveScannerException;
import common.error.FileNotExistException;
import common.error.IllegalRecursionException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Stack;

public class FileManager {
    private static Stack<File> files = new Stack<>();
    private static Stack<Scanner> scanners = new Stack<>();

    public static boolean isInFile() {
        return !files.empty();
    }

    public static File getFileOnTop() {
        return files.pop();
    }

    public Scanner getScannerOnTop() {
        return scanners.peek();
    }

    public static File createFile(String path) throws FileNotExistException {
        File file = new File(path.trim());
        if (file == null) {
            throw new FileNotExistException("Указанный файл" + path + "не существует");
        }
        return file;
    }

    public void createFileInExecute(String path) throws FileNotExistException, IllegalRecursionException {
        File file = createFile(path.trim());
        haveRecursion(file);
        try {
            Scanner scanner = new Scanner(new InputStreamReader(new FileInputStream(file)));
            scanners.add(scanner);
            files.add(file);
        } catch (FileNotFoundException e) {
            throw new FileNotExistException("Указанный файл " + path + " не существует");
        }
    }

    public void haveRecursion (File file) throws IllegalRecursionException {
        if (files.search(file) != -1) {
            files.clear();
            for (Scanner scanner: scanners) {
                scanner.close();
            }
            scanners.clear();
            throw new IllegalRecursionException("Замечена рекурсия!");
        }
    }

    public Scanner getScanner() throws CantHaveScannerException {
        boolean notHaveLine = true;
        if (scanners.size() == 0) {
            throw new CantHaveScannerException();
        }
        while (notHaveLine) {
            if (getScannerOnTop().hasNext()) {
                return getScannerOnTop();
            } else {
                scanners.pop();
                files.pop();
                if (scanners.size() == 0) {
                    throw new CantHaveScannerException();
                }
            }
        }
        throw new CantHaveScannerException();
    }
}
