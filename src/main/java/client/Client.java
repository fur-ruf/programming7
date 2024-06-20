package client;

import common.error.ConfigFileException;
import common.tools.Color;
import common.interaction.Request;
import common.interaction.Response;
import common.tools.GettingProperty;
import common.tools.TakingConsole;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class Client {
    private String host;
    private int port;
    private int reconnectionTimeout;
    private int reconnectionAttempts;
    private int maxReconnectionAttempts;
    private TakingConsole console = new TakingConsole();
    private Socket socket;
    private ObjectOutputStream serverWriter;
    private ObjectInputStream serverReader;


    public Client() throws ConfigFileException {
        GettingProperty gettingProperty = new GettingProperty();
        try {
            this.host = gettingProperty.getProperty("host");
            this.port = Integer.parseInt(gettingProperty.getProperty("port"));
            this.reconnectionTimeout = Integer.parseInt(gettingProperty.getProperty("reconnectionTimeout"));
            this.maxReconnectionAttempts = Integer.parseInt(gettingProperty.getProperty("maxReconnectionAttempts"));
        } catch (NumberFormatException e) {
            throw new ConfigFileException("Неверные данные в файле настроек");
        }
    }

    public Response sendAndAskResponse(Request request){
        while (true) {
            try {
                if(Objects.isNull(serverWriter)) throw new IOException();
                serverWriter.writeObject(request);
                serverWriter.flush();
                this.serverReader = new ObjectInputStream(socket.getInputStream());
                Response response = (Response) serverReader.readObject();
                this.disconnectFromServer();
                reconnectionAttempts = 0;
                return response;
            } catch (IOException e) {
                if (reconnectionAttempts == 0){
                    connectToServer();
                    reconnectionAttempts++;
                    continue;
                } else {
                    console.printError("Соединение с сервером разорвано");
                }
                try {
                    reconnectionAttempts++;
                    if (reconnectionAttempts >= maxReconnectionAttempts) {
                        console.printError("Превышено максимальное количество попыток соединения с сервером");
                    }
                    console.println("Повторная попытка через " + reconnectionTimeout / 1000 + " секунд");
                    Thread.sleep(reconnectionTimeout);
                    connectToServer();
                } catch (Exception exception) {
                    console.printError("Попытка соединения с сервером неуспешна");
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void connectToServer(){
        try{
            if(reconnectionAttempts > 0) console.println("Попытка повторного подключения", Color.CYAN);
            this.socket = new Socket(host, port);
            this.serverWriter = new ObjectOutputStream(socket.getOutputStream());
        } catch (IllegalArgumentException e){
            console.printError("Адрес сервера введен некорректно");
        } catch (IOException e) {
            console.printError("Произошла ошибка при соединении с сервером");
        }
    }

    public void disconnectFromServer(){
        try {
            this.socket.close();
            serverWriter.close();
            serverReader.close();
        } catch (IOException e) {
            console.printError("Не подключен к серверу");
        }
    }
}
