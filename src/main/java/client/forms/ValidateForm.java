package client.forms;

import common.input.FileReader;
import common.input.Input;
import common.input.UserReader;
import common.IOHandler.FileManager;
import common.tools.InteractConsole;
import common.tools.SilentConsole;
import common.tools.TakingConsole;

public abstract class ValidateForm {
    protected InteractConsole console;
    protected Input scanner;
    public ValidateForm() {
        if (FileManager.isInFile()) {
            scanner = new FileReader();
            console = new SilentConsole();
        } else {
            scanner = new UserReader();
            console = new TakingConsole();
        }
    }
}
