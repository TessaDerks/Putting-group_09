package physics;

import org.jetbrains.annotations.NotNull;

public class Tree {
    private final Vector2d p;
    private final double r;

    /**
     *
     * @param _p
     * @param _r
     */
    public Tree(Vector2d _p, double _r){
        p = _p;
        r = _r;
    }

    /**
     *
     * @param bp
     * @return
     */
    public boolean treeHit(@NotNull Vector2d bp){
        return (Math.sqrt(Math.pow(bp.get_x() - p.get_x(),2) + Math.pow(bp.get_y() - p.get_y(),2))) <= r;
    }

    /**
     *
     * @param bp
     * @return
     */
    public boolean AITreeHit(@NotNull Vector2d bp){
        return (Math.sqrt(Math.pow(bp.get_x() - p.get_x(),2) + Math.pow(bp.get_y() - p.get_y(),2))) <= r*1.2;
    }

    public Vector2d getP() {
        return p;
    }
}

