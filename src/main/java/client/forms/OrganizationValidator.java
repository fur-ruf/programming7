package client.forms;

import common.organization.Address;
import common.organization.Coordinates;
import common.organization.Organization;
import common.organization.OrganizationType;
import common.error.CantHaveScannerException;
import common.error.ErrorInFileException;
import common.IOHandler.FileManager;
import common.tools.Color;

public class OrganizationValidator extends ValidateForm {

    public OrganizationValidator() {
        super();
    }

    public Organization build() throws ErrorInFileException, CantHaveScannerException {
        return new Organization(askName(), askCoordinates(), askAnnualTurnover(), askEmployeesCount(), askType(), askOfficialAddress());
    }

    private String askName() throws ErrorInFileException, CantHaveScannerException {
        String input;
        while (true) {
            console.println("Введите имя", Color.PURPLE);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                console.printError("Имя не может быть пустым");
                if (FileManager.isInFile()) {
                    throw new ErrorInFileException();
                }
            } else {
                return input;
            }
        }
    }

    private Coordinates askCoordinates() throws ErrorInFileException, CantHaveScannerException {
        return new CoordinatesValidator().build();
    }

    private Float askAnnualTurnover() throws ErrorInFileException, CantHaveScannerException {
        String input;
        while(true) {
            console.println("Введите годовой оборот организации (типа float)", Color.PURPLE);
            input = scanner.nextLine().trim();
            try {
                float annualTurnover = Float.parseFloat(input);
                if (annualTurnover <= 0) throw new NumberFormatException();
                return annualTurnover;
            } catch (NumberFormatException e) {
                console.printError("Введенная строка некорректна. Необходимо число вида '12.3' или '12.3f', большее 0");
                if (FileManager.isInFile()) {
                    throw new ErrorInFileException();
                }
            }
        }
    }

    private Long askEmployeesCount() throws ErrorInFileException, CantHaveScannerException {
        String input;
        while(true) {
            console.println("Введите количество работников организации (типа long)", Color.PURPLE);
            input = scanner.nextLine().trim();
            try {
                long employeesCount = Long.parseLong(input);
                if (employeesCount <= 0) throw new NumberFormatException();
                return employeesCount;
            } catch (NumberFormatException e) {
                console.printError("Введенная строка некорректна. Необходимо число больше 0");
                if (FileManager.isInFile()) {
                    throw new ErrorInFileException();
                }
            }
        }
    }

    private OrganizationType askType() throws ErrorInFileException, CantHaveScannerException {
        return new OrganizationTypeValidator().build();
    }

    private Address askOfficialAddress() throws ErrorInFileException, CantHaveScannerException {
        return new AddressValidator().build();
    }
}
