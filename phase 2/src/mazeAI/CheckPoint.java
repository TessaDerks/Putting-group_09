package mazeAI;

public class CheckPoint implements GraphNode {
    private final String id;
    private final double x;
    private final double y;

    public CheckPoint(double _x, double _y){
        x = _x;
        y = _y;
        id = "(" + x + ";" + y + ")";
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String getId() {
        return id;
    }
}
