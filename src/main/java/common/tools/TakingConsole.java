package common.tools;

public class TakingConsole extends InteractConsole{
    @Override
    public void print(String message, Color color) {
        System.out.print(color.getColor() + message + Color.NORMAL.getColor());
    }
    @Override
    public void println(String message, Color color) {
        System.out.println(color.getColor() + message + Color.NORMAL.getColor());
    }

    @Override
    public void println(String message) {}
}
