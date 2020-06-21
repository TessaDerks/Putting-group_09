package physics;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Stijn Hennissen
 */
class SIESolverTest {

    @org.junit.jupiter.api.Test
    void actTimestep_rightAnglesNotNaNOrInfinite() {
        SIESolver solver = new SIESolver(new Vector2d(0,0));
        // 0.
        solver.set_v(Tools.velFromAngle(0,10));
        solver.actTimestep();
        Assert.assertFalse(Double.isNaN(solver.get_p().get_x()));
        Assert.assertFalse(Double.isInfinite(Math.abs(solver.get_p().get_x())));
        Assert.assertFalse(Double.isNaN(solver.get_p().get_y()));
        Assert.assertFalse(Double.isInfinite(Math.abs(solver.get_p().get_y())));
        // 90.
        solver.set_v(Tools.velFromAngle(90,10));
        solver.actTimestep();
        Assert.assertFalse(Double.isNaN(solver.get_p().get_x()));
        Assert.assertFalse(Double.isInfinite(Math.abs(solver.get_p().get_x())));
        Assert.assertFalse(Double.isNaN(solver.get_p().get_y()));
        Assert.assertFalse(Double.isInfinite(Math.abs(solver.get_p().get_y())));
        // 180.
        solver.set_v(Tools.velFromAngle(180,10));
        solver.actTimestep();
        Assert.assertFalse(Double.isNaN(solver.get_p().get_x()));
        Assert.assertFalse(Double.isInfinite(Math.abs(solver.get_p().get_x())));
        Assert.assertFalse(Double.isNaN(solver.get_p().get_y()));
        Assert.assertFalse(Double.isInfinite(Math.abs(solver.get_p().get_y())));
        // 270.
        solver.set_v(Tools.velFromAngle(270,10));
        solver.actTimestep();
        Assert.assertFalse(Double.isNaN(solver.get_p().get_x()));
        Assert.assertFalse(Double.isInfinite(Math.abs(solver.get_p().get_x())));
        Assert.assertFalse(Double.isNaN(solver.get_p().get_y()));
        Assert.assertFalse(Double.isInfinite(Math.abs(solver.get_p().get_y())));
    }

}