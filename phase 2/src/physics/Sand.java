package physics;

import org.jetbrains.annotations.NotNull;

/**
 * @author Stijn Hennissen
 */
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
    public double coordInSand(@NotNull Vector2d c){
        double r = -1;
        if(((c.get_x()>=topLeft.get_x())&&(c.get_x()<=bottomRight.get_x())&& ((c.get_y()<=topLeft.get_y())&&(c.get_y()>=bottomRight.get_y())))){
            r = friction;
        }
        return r;
    }

    //<editor-fold desc="Getters & Setters">
    public double getFriction() {
        return friction;
    }

    //</editor-fold>
}
