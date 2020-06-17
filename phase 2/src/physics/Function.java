package physics;

import org.jetbrains.annotations.NotNull;

public class Function implements Function2d {
    private final double delta = 1e-10;
    private static String masterFunction;
    private static double cap;

    public Function(String function, double _cap){
        masterFunction = ShuntingYard.postfix(function);
        cap = _cap;
    }

    @Override
    public double evaluate(Vector2d p) {
        double z = Math.min(cap,PostFixCalculator.calculate(masterFunction,p));
        return z;
    }

    @Override
    public Vector2d gradient(@NotNull Vector2d p) {
        double dx = (this.evaluate(new Vector2d(p.get_x() + delta,p.get_y())) - evaluate(new Vector2d(p.get_x(),p.get_y())))/delta;
        double dy = (this.evaluate(new Vector2d(p.get_x(),p.get_y() + delta)) - evaluate(new Vector2d(p.get_x(),p.get_y())))/delta;
        dx = Tools.advRound(dx,4);
        dy = Tools.advRound(dy,4);
        return new Vector2d(dx,dy);
    }
}
