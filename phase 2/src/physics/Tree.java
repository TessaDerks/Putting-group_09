package physics;

import org.jetbrains.annotations.NotNull;

public class Tree {
    private final Vector2d p;
    private final double r;

    /**
     * Constructor
     * @param _p Vector2d, position of the center of the tree
     * @param _r double, radius of the tree
     */
    public Tree(Vector2d _p, double _r){
        p = _p;
        r = _r;
    }

    /**
     * Check if a specified point collides with the tree
     * @param bp Vector2d, position of the point
     * @return boolean, true when the point collides with the tree, false otherwise
     */
    public boolean treeHit(@NotNull Vector2d bp){
        return (Math.sqrt(Math.pow(bp.get_x() - p.get_x(),2) + Math.pow(bp.get_y() - p.get_y(),2))) <= r;
    }

    /**
     * Check if a specified point collides or is too close to the tree
     * @param bp Vector2d, position of the point
     * @return boolean, true when the point collides or is too close to with the tree, false otherwise
     */
    public boolean AITreeHit(@NotNull Vector2d bp){
        return (Math.sqrt(Math.pow(bp.get_x() - p.get_x(),2) + Math.pow(bp.get_y() - p.get_y(),2))) <= r*1.2;
    }

    public Vector2d getP() {
        return p;
    }
}

