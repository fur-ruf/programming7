package common.input;

import client.managers.ScannerManager;

import java.util.Scanner;

public class UserReader extends Input{
    private final static Scanner scanner = ScannerManager.getScanner();

    public String nextLine() {
        return scanner.nextLine();
    }
}
