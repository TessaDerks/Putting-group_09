package mazeAI;

public class CheckPoint implements GraphNode {
    private final String id;
    private final double x;
    private final double y;

    /**
     *
     * @param _x double, x coordinate
     * @param _y double, y coordinate
     */
    public CheckPoint(double _x, double _y){
        x = _x;
        y = _y;
        id = "(" + x + "," + y + ")";
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public String getId() {
        return id;
    }
}
