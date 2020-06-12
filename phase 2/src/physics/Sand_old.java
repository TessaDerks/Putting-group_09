package physics;

public class Sand_old {
    private Vector2d p;
    private double r;
    private double friction;



    public Sand_old(Vector2d _p, double _r, double _friction){
        p = _p;
        r = _r;
        friction = _friction;

    }

    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Needs to be changed still.
    public boolean sandHit(Vector2d bp){
        return (Math.sqrt(Math.pow(bp.get_x() - p.get_x(),2) + Math.pow(bp.get_y() - p.get_y(),2))) <= r;
    }

    public Vector2d getP() {
        return p;
    }

    public double getFriction() {
        return friction;
    }
}
