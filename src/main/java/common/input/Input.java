package common.input;

import common.error.CantHaveScannerException;

public abstract class Input {
    public abstract String nextLine() throws CantHaveScannerException;
}
