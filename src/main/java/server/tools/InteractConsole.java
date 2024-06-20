package server.tools;

public abstract class InteractConsole {
    public static String printError(String message) {
        return Color.RED.getColor() + message;
    }
    public static String printGoodbye(String message) {
        return Color.YELLOW .getColor()+ message + Color.NORMAL.getColor();
    }
    public static String printSuccess(String message) {
        return Color.BLUE.getColor() + message + Color.NORMAL.getColor();
    }
    public static String printMessage(String message) {
        return Color.WHITE.getColor() + message + Color.NORMAL.getColor();
    }
    public static String printResult(String message) {
        return Color.PURPLE.getColor() + message + Color.NORMAL.getColor();
    }
}
