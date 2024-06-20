package server;

import common.IOHandler.FileManager;
import common.error.*;
import common.interaction.Request;
import common.interaction.Response;
import common.interaction.User;
import common.tools.GettingProperty;
import common.tools.TakingConsole;
import server.collectionHandler.CollectionWriter;
import server.managers.*;
import server.tools.InteractConsole;
import server.collectionHandler.CollectionReader;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Server {
    private int port;
    private TakingConsole console = new TakingConsole();
    private ServerSocketChannel ss;
    private SocketChannel socketChannel;
    private CommandManager commandManager;
    private CollectionManager collectionManager;
    private static final Logger serverLogger = LogManager.getLogger("server");
    private final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

    public Server(CommandManager commandManager, CollectionManager collectionManager) throws ConfigFileException {
        GettingProperty gettingProperty = new GettingProperty();
        String fileName = gettingProperty.getProperty("path");
        this.collectionManager = collectionManager;
        try {
            this.port = Integer.parseInt(gettingProperty.getProperty("port"));
        } catch (NumberFormatException e) {
            throw new ConfigFileException("Неверные данные в файле настроек");
        }
        CollectionReader reader = new CollectionReader(collectionManager);
        try {
            reader.getCollection(fileName);
        } catch (FileNotExistException | XMLErrorException | RepeatIdException e) {
            throw new ConfigFileException("Неверные данные в файле настроек. Чтение из файла невозможно. " + e.getMessage());
        }
        System.out.println(InteractConsole.printSuccess("Чтение выполнено!"));
        this.commandManager = commandManager;
    }

    public void run() throws ConfigFileException {
        try{
            openServerSocket();
            serverLogger.info("--------------------------------------------------------------------");
            serverLogger.info("-----------------СЕРВЕР УСПЕШНО ЗАПУЩЕН-----------------------------");
            serverLogger.info("--------------------------------------------------------------------");
            while (true) {
                FutureManager.checkAllFutures();
                try{
                    fixedThreadPool.submit(new ConnectionManager(commandManager, connectToClient()));
                } catch (ConnectionErrorException  ignored){}
            }
        } catch (OpeningServerException e) {
            serverLogger.fatal("Сервер не может быть запущен");
        }
        stop();
    }

    private void openServerSocket() throws OpeningServerException{
        try {
            SocketAddress socketAddress = new InetSocketAddress(port);
            ss = ServerSocketChannel.open();
            ss.bind(socketAddress);
        } catch (IllegalArgumentException exception) {
            serverLogger.error("Порт находится за пределами возможных значений");
            throw new OpeningServerException("");
        } catch (IOException exception) {
            serverLogger.error("Произошла ошибка при попытке использовать порт " + port);
            throw new OpeningServerException("");
        }
    }

    private SocketChannel connectToClient() throws ConnectionErrorException{
        try {
            ss.socket().setSoTimeout(50);
            socketChannel = ss.socket().accept().getChannel();
            serverLogger.info("Соединение с клиентом успешно установлено.");
            return socketChannel;
        } catch (IOException e) {
            throw new ConnectionErrorException("");
        }
    }

    private void savingInFile() throws ConfigFileException {
        CollectionWriter writter = new CollectionWriter(this.collectionManager);
        try {
            writter.saveObjects(FileManager.createFile(new GettingProperty().getProperty("path")));
        } catch (FileNotExistException e) {
            throw new ConfigFileException("Ошибка в файле на сервере");
        }
    }

    private void stop() throws ConfigFileException {
        class ClosingSocketException extends Exception{}
        savingInFile();
        try {
            if (socketChannel == null) throw new ClosingSocketException();
            socketChannel.close();
            ss.close();
            serverLogger.info("все соединения закрыты");
        } catch (ClosingSocketException exception) {
            serverLogger.fatal("Невозможно завершить работу еще не запущенного сервера!");
        } catch (IOException exception) {
            serverLogger.fatal("Произошла ошибка при завершении работы сервера!");
        }
    }
}
