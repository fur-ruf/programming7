package server.managers;

import common.interaction.Request;
import common.interaction.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.channels.SocketChannel;
import java.util.concurrent.*;

public class ConnectionManager implements Runnable{
    private final CommandManager commandManager;
    private static final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(8);
    private static final ForkJoinPool forkJoinPool = new ForkJoinPool();
    private final SocketChannel clientSocket;

    static final Logger connectionManagerLogger = LogManager.getLogger(ConnectionManager.class);

    public ConnectionManager(CommandManager commandManager, SocketChannel clientSocket) {
        this.commandManager = commandManager;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run(){
        Request userRequest = null;
        Response responseToUser = null;
        try {
            ObjectInputStream clientReader = new ObjectInputStream(clientSocket.socket().getInputStream());
            ObjectOutputStream clientWriter = new ObjectOutputStream(clientSocket.socket().getOutputStream());
            while (true) {
                userRequest = (Request) clientReader.readObject();
                connectionManagerLogger.info("Получен запрос с командой " + userRequest.getCommandName(), userRequest);
                if (AccessManager.access(userRequest.getUser(), userRequest.getCommandName())) {
                    FutureManager.addNewForkJoinPoolFuture(forkJoinPool.submit(new RequestManager(commandManager, userRequest, clientWriter)));
                } else {
                    connectionManagerLogger.info("Юзер не одобрен");
                    responseToUser = new Response("Неверный пользователь!");
                    submitNewResponse(new ConnectionManagerPool(responseToUser, clientWriter));
                }
            }
        } catch (ClassNotFoundException exception) {
            connectionManagerLogger.fatal("Произошла ошибка при чтении полученных данных!");
        }catch (CancellationException exception) {
            connectionManagerLogger.warn("При обработке запроса произошла ошибка многопоточности!");
        } catch (InvalidClassException | NotSerializableException exception) {
            connectionManagerLogger.error("Произошла ошибка при отправке данных на клиент!");
        } catch (IOException exception) {
            if (userRequest == null) {
                connectionManagerLogger.error("Непредвиденный разрыв соединения с клиентом!");
            } else {
                connectionManagerLogger.info("Клиент успешно отключен от сервера!");
            }
        }
    }

    public static void submitNewResponse(ConnectionManagerPool connectionManagerPool){
        Runnable task = () -> {
            try {
                connectionManagerLogger.info("Отправлен ответ");
                System.out.println(connectionManagerPool.response().getResponse());
                connectionManagerPool.objectOutputStream().writeObject(connectionManagerPool.response());
                connectionManagerPool.objectOutputStream().flush();
            } catch (IOException e) {
                connectionManagerLogger.error("Не удалось отправить ответ");
                connectionManagerLogger.debug(e);
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }
}
