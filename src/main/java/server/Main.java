package server;

import common.error.ConfigFileException;
import common.tools.GettingProperty;
import common.tools.InteractConsole;
import common.tools.TakingConsole;
import server.managers.CollectionManager;
import server.managers.CommandManager;
import server.managers.DatabaseHandler;
import server.managers.RequestManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Main {
    private static final TakingConsole console = new TakingConsole();

    public static void main(String[] args) {
        Server server = null;
        try {
            CollectionManager collectionManager = new CollectionManager();
            server = new Server(new CommandManager(collectionManager), collectionManager);
            server.run();
        } catch (ConfigFileException e) {
            InteractConsole.printError(e.getMessage());
        }
    }
}