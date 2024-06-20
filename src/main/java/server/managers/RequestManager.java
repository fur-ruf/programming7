package server.managers;

import common.interaction.Request;

import java.io.ObjectOutputStream;
import java.util.concurrent.Callable;

public class RequestManager implements Callable<ConnectionManagerPool> {
    private CommandManager commandManager;
    private Request request;
    private ObjectOutputStream objectOutputStream;

    public RequestManager(CommandManager commandManager, Request request, ObjectOutputStream objectOutputStream) {
        this.commandManager = commandManager;
        this.request = request;
        this.objectOutputStream = objectOutputStream;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    @Override
    public ConnectionManagerPool call() {
        return new ConnectionManagerPool(commandManager.execute(request), objectOutputStream);
    }
}
