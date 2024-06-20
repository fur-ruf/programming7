package client.forms;

import common.organization.Coordinates;
import common.error.CantHaveScannerException;
import common.error.ErrorInFileException;
import common.IOHandler.FileManager;
import common.tools.Color;

public class CoordinatesValidator extends ValidateForm {

    public CoordinatesValidator() {
        super();
    }

    public Coordinates build() throws ErrorInFileException, CantHaveScannerException {
        this.console.println("Введите координаты в следующем формате (типа double): х у", Color.PURPLE);

        while (true) {
            String input = scanner.nextLine();
            String[] values = input.split(" ");
            try {
                Double x = Double.parseDouble(values[0]);
                Double y = Double.parseDouble(values[1]);
                return new Coordinates(x, y);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                console.printError("Введенная строка некорректна. Необходимы два числа вида '12.3 13.8'");
                if (FileManager.isInFile()) {
                    throw new ErrorInFileException();
                }
            }
        }
    }
}
