package client;

import client.managers.ClientWorkManager;
import common.error.ConfigFileException;
import common.tools.InteractConsole;
import common.tools.TakingConsole;


public class App1 {
    private static String host;
    private static int port;
    private static TakingConsole console = new TakingConsole();


    public static void main(String[] args) {
        Client client = null;
        try {
            client = new Client();
        } catch (ConfigFileException e) {
            InteractConsole.printError(e.getMessage());
            return;
        }
        new ClientWorkManager(client).work();
    }
}