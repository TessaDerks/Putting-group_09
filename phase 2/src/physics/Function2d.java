package physics;

public class Function2d{
    private double delta = 1e-10;
    private static String masterFunction;
    private static double cap;

    public Function2d(String function, double _cap){
        masterFunction = ShuntingYard.postfix(function);
        cap = _cap;
    }

    public static double evaluate(Vector2d p) {
        double z = Math.min(cap,PostFixCalculator.calculate(masterFunction,p));
        return z;
    }

    public Vector2d gradient(Vector2d p) {
        double dx = (evaluate(new Vector2d(p.get_x() + delta,p.get_y())) - evaluate(new Vector2d(p.get_x(),p.get_y())))/delta;
        double dy = (evaluate(new Vector2d(p.get_x(),p.get_y() + delta)) - evaluate(new Vector2d(p.get_x(),p.get_y())))/delta;
        dx = Tools.advRound(dx,4);
        dy = Tools.advRound(dy,4);
        return new Vector2d(dx,dy);
    }
}
