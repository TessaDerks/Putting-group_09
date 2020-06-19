package physics;

public class Vector2d {
    private final double x;
    private final double y;

    /**
     * Constructor
     * @param _x double
     * @param _y double
     */
    public Vector2d(double _x, double _y){
        x = _x;
        y = _y;
    }

    public double get_x() {
        return x;
    }

    public double get_y() {
        return y;
    }

    @Override
    public String toString() {
        return "("+x+","+y+")";
    }
}
