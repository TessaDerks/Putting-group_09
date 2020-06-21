package physics;

import org.jetbrains.annotations.NotNull;

/**
 * @author Stijn Hennissen
 */
public class Function implements Function2d {
    private static String masterFunction;
    private static double cap;

    /**
     *
     * @param function
     * @param _cap
     */
    public Function(String function, double _cap){
        masterFunction = ShuntingYard.postfix(function);
        cap = _cap;
    }

    @Override
    public double evaluate(Vector2d p) {
        return Math.min(cap,PostFixCalculator.calculate(masterFunction,p));
    }

    @Override
    public Vector2d gradient(@NotNull Vector2d p) {
        double delta = 1e-10;
        double dx = (this.evaluate(new Vector2d(p.get_x() + delta,p.get_y())) - evaluate(new Vector2d(p.get_x(),p.get_y())))/ delta;
        double dy = (this.evaluate(new Vector2d(p.get_x(),p.get_y() + delta)) - evaluate(new Vector2d(p.get_x(),p.get_y())))/ delta;
        dx = Tools.advRound(dx,4);
        dy = Tools.advRound(dy,4);
        return new Vector2d(dx,dy);
    }
}
