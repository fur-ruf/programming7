package client.managers;

import client.Client;

import client.forms.UserValidator;
import com.thoughtworks.xstream.XStreamException;
import common.IOHandler.FileManager;
import common.error.*;
import common.input.FileReader;
import common.interaction.Request;
import common.interaction.Response;
import common.interaction.User;
import common.tools.Color;
import common.tools.InteractConsole;
import common.tools.TakingConsole;


import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

public class ClientWorkManager {
    private final TakingConsole console = new TakingConsole();
    private final Scanner userScanner = ScannerManager.getScanner();
    private final Client client;
    private static ClientCommandManager commandManager = new ClientCommandManager();
    private final FileReader fileReader = new FileReader();
    private FileManager fileManager = new FileManager();
    private User user;

    public ClientWorkManager(Client client) {
        this.client = client;
    }

    public void work() {
        console.println("Пиривет! Мчись вперёд навстречу приключениям и бага... кхм, кхм, это шутка, плохая шутка. Начнём!", Color.PURPLE);
        while (true) {
            try {
                if (Objects.isNull(user)) {
                    Response response = null;
                    boolean isLogin = true;
                    do {
                        if(!Objects.isNull(response)) {
                            console.println(response.getResponse());
                            console.println( (isLogin)
                                    ? "Такой связки логин-пароль нет, попробуйте снова"
                                    : "Этот логин уже занят, попробуйте снова!");
                        }
                        UserValidator userForm = new UserValidator();
                        isLogin = userForm.askIfLogin();
                        try {
                            user = new UserValidator().build();
                        } catch (ErrorInLoginException | ErrorInPasswordException e) {
                            console.println("Неверный пароль или логин");
                        }
                        client.connectToServer();
                        if (isLogin) {
                            response = client.sendAndAskResponse(new Request("entry", user));
                            System.out.println(response.getResponse());
                        } else {
                            response = client.sendAndAskResponse(new Request("register", user));
                            System.out.println(response.getResponse());
                        }
                    } while (!response.getResponse().equals("Доступ разрешён"));
                    System.out.println(user);
                    console.println("Вы успешно зашли в аккаунт");
                }

                if (!userScanner.hasNext()) throw new ExitException("");
                String message = userScanner.nextLine();
                try {
                    Request request = commandExecute(message);
                    request.setUser(user);
                    switch (request.getStatus()){
                        case COMMON -> {
                            workCommon(request);
                        }
                        case ONLY_CLIENT_COMMAND -> {}
                        case EXECUTE_COMMAND -> {
                            workExecute(request);
                        }
                        case EXIT -> {
                            throw new ExitException("Настала пора прощаться ;) До встречи!");
                        }
                    }
                } catch (InvalidArgumentException | FileNotExistException | CommandNotExistException e) {
                    InteractConsole.printError(e.getMessage());
                } catch (IllegalRecursionException | ErrorInFileException | NoSuchElementException | XStreamException e) {
                    InteractConsole.printError(e.getMessage());
                    InteractConsole.printError("Чтение из файла прекращено");
                } catch (CantHaveScannerException e) {
                    InteractConsole.printMessage("Чтение из файла прекращено");
                }
            } catch (ExitException e) {
                console.println(e.getMessage(), Color.PURPLE);
                break;
            }
            catch (CantHaveScannerException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void workExecute(Request request) throws IllegalRecursionException, FileNotExistException, CantHaveScannerException, ErrorInFileException, CommandNotExistException, InvalidArgumentException, ExitException {
        fileManager.createFileInExecute(request.getPath());
        InteractConsole.printSuccess("Файл успешно найден!");
        while (FileManager.isInFile()) {
            String commandWithArg = fileReader.nextLine().trim() + " ";
            Request requestInExecute = commandExecute(commandWithArg);
            requestInExecute.setUser(user);
            switch (requestInExecute.getStatus()){
                case COMMON -> {
                    workCommon(requestInExecute);
                }
                case ONLY_CLIENT_COMMAND -> {}
                case EXECUTE_COMMAND -> {
                    workExecute(requestInExecute);
                }
                case EXIT -> {
                    throw new ExitException("Настала пора прощаться ;) До встречи!");
                }
            }
        }
    }

    private void workCommon(Request request) {
        client.connectToServer();
        Response response = client.sendAndAskResponse(request);
        if (response != null) {
            this.printResponse(response);
        }
    }

    private void printResponse(Response response){
        System.out.println(response.getResponse());
    }

    public static Request commandExecute(String commandNameWithArgs) throws CommandNotExistException, InvalidArgumentException, IllegalRecursionException, FileNotExistException, ErrorInFileException, CantHaveScannerException {
        String[] userCommand = (commandNameWithArgs.trim() + " ").split(" ", 2);
        String commandName = userCommand[0];
        String arg = userCommand[1];
        if (commandName.equals("")) {
            throw new CommandNotExistException("Введенная команда не существует");
        }
        return commandManager.execute(commandName, arg);
    }
}