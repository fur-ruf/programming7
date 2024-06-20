package client.forms;

import common.error.CantHaveScannerException;
import common.error.ErrorInLoginException;
import common.error.ErrorInPasswordException;
import common.interaction.User;
import common.tools.Color;

import java.util.Objects;

public class UserValidator extends ValidateForm{
    public User build() throws CantHaveScannerException, ErrorInLoginException, ErrorInPasswordException {
        return new User(askLogin(), askPassword());
    }

    public boolean askIfLogin() throws CantHaveScannerException {
        while (true) {
            console.println("У вас уже есть аккаунт? да(yes)/нет(no)  ", Color.PURPLE);
            String input = scanner.nextLine().trim().toLowerCase();
            switch (input){
                case "y", "yes", "да", "д" -> {
                    return true;
                }
                case "n", "no", "нет", "н" -> {
                    return false;
                }
                default -> console.printError("Ответ не распознан");
            }
        }
    }
    private String askLogin() throws CantHaveScannerException, ErrorInLoginException {
        String login;
        console.println("Введите ваш логин", Color.PURPLE);
        login = scanner.nextLine().trim();
        if (login.isEmpty()) {
            throw new ErrorInLoginException("Ошибка в записи логина");
        } else {
            return login;
        }
    }

    private String askPassword() throws CantHaveScannerException, ErrorInPasswordException {
        String password;
        console.println("Введите пароль", Color.PURPLE);
        password = scanner.nextLine().trim();
        if (password.isEmpty()){
            throw new ErrorInPasswordException("Ошибка в записи логина");
        }
        else {
            return password;
        }
    }
}



