package common.tools;

public abstract class InteractConsole {
    public abstract void print(String message, Color color);
    public abstract void println(String message, Color color);
    public abstract void println(String message);
    public static void printError(String message) {
        System.out.println(Color.RED.getColor() + message + Color.NORMAL.getColor());
    }
    public static void printGoodbye(String message) {
        System.out.println(Color.YELLOW .getColor()+ message + Color.NORMAL.getColor());
    }
    public static void printSuccess(String message) {
        System.out.println(Color.WHITE.getColor() + message + Color.NORMAL.getColor());
    }
    public static void printMessage(String message) {
        System.out.println(Color.BLUE.getColor() + message + Color.NORMAL.getColor());
    }
}
