package client.managers;

import java.util.Scanner;

public class ScannerManager {
    private static Scanner scanner = new Scanner(System.in);

    public static Scanner getScanner() {
        return scanner;
    }

}
