package client.forms;

import common.organization.Address;
import common.error.CantHaveScannerException;
import common.error.ErrorInFileException;
import common.IOHandler.FileManager;
import common.tools.Color;

public class AddressValidator extends ValidateForm{
    public AddressValidator() {
        super();
    }

    public Address build() throws ErrorInFileException, CantHaveScannerException {
        console.println("Введите адрес организации в следующем формате: улица почтовый_индекс", Color.PURPLE);

        while (true) {
            String input = scanner.nextLine();

            String[] words = input.split(" ");
            if (words.length < 2) {
                console.printError("Введенная строка некорректна. Необходима непустая строка");
                if (FileManager.isInFile()) {
                    throw new ErrorInFileException();
                }
            }
            else {
                if (words[words.length - 1].length() > 18) {
                    console.printError("Введенная строка некорректна. Индекс не должен быть больше 18 символов");
                    if (FileManager.isInFile()) {
                        throw new ErrorInFileException();
                    }
                } else {
                    String[] wordStreet = new String[words.length - 1];
                    for (int i = 0; i < wordStreet.length; i++) {
                        wordStreet[i] = words[i];
                    }
                    return new Address(String.join(" ", wordStreet), words[words.length - 1]);
                }
            }
        }
    }
}

