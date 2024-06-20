package common.tools;

public class SilentConsole extends InteractConsole{
    @Override
    public void print(String message, Color color) {}
    @Override
    public void println(String message, Color color) {}
    @Override
    public void println(String message) {}
}
