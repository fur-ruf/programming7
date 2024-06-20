package client.forms;

import common.organization.OrganizationType;
import common.error.CantHaveScannerException;
import common.error.ErrorInFileException;
import common.IOHandler.FileManager;
import common.tools.Color;

public class OrganizationTypeValidator extends ValidateForm {

    public OrganizationTypeValidator() {
        super();
    }

    public OrganizationType build() throws ErrorInFileException, CantHaveScannerException {
        this.console.print("Доступные типы компаний: ", Color.PURPLE);
        this.console.println(OrganizationType.getValues(), Color.PURPLE);

        while (true) {
            this.console.println("Выберите тип компании: ", Color.PURPLE);
            String input = this.scanner.nextLine().trim();

            try {
                return OrganizationType.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                this.console.printError("Тип компании не найден");
                if (FileManager.isInFile()) {
                    throw new ErrorInFileException();
                }
            }
        }
    }
}
