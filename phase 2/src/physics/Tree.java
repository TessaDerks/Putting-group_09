package physics;

public class Tree {
    private Vector2d p;
    private double r;

    public Tree(Vector2d _p, double _r){
        p = _p;
        r = _r;
    }

    public boolean treeHit(Vector2d bp){
        return (Math.sqrt(Math.pow(bp.get_x() - p.get_x(),2) + Math.pow(bp.get_y() - p.get_y(),2))) <= r;
    }

    public Vector2d getP() {
        return p;
    }
}

