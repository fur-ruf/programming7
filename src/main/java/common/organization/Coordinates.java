package common.organization;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private Double x;
    private Double y;

    public Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
