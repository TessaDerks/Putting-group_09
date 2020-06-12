package physics;

public class Sand {
    private Vector2d topLeft;
    private Vector2d bottomRight;
    private double friction = 0.9;

    public Sand(Vector2d _topLeft, Vector2d _bottomRight, double _friction){
        topLeft = _topLeft;
        bottomRight = _bottomRight;
        friction = _friction;
    }

    public boolean coordInSand(Vector2d c){
        boolean r = false;
        if(((c.get_x()>=topLeft.get_x())&&(c.get_x()<=bottomRight.get_x())&& ((c.get_y()<=topLeft.get_y())&&(c.get_y()>=bottomRight.get_y())))){
            r = true;
        }
        return r;
    }

    //<editor-fold desc="Getters & Setters">
    public double getFriction() {
        return friction;
    }

    public Vector2d getBottomRight() {
        return bottomRight;
    }

    public Vector2d getTopLeft() {
        return topLeft;
    }

    public void setFriction(double friction) {
        this.friction = friction;
    }
    //</editor-fold>
}
