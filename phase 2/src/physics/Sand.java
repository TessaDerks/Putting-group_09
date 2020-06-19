package physics;

import org.jetbrains.annotations.NotNull;

public class Sand {
    private final Vector2d topLeft;
    private final Vector2d bottomRight;
    private double friction;

    /**
     *
     * @param _topLeft
     * @param _bottomRight
     * @param _friction
     */
    public Sand(Vector2d _topLeft, Vector2d _bottomRight, double _friction){
        topLeft = _topLeft;
        bottomRight = _bottomRight;
        friction = _friction;
    }

    /**
     *
     * @param c
     * @return
     */
    public boolean coordInSand(@NotNull Vector2d c){
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

    //</editor-fold>
}
