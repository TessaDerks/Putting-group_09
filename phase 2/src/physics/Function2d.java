package physics;

public interface Function2d {
    final double delta = 1e-10;
    static String masterFunction = null;
    static double cap = 900;

    double evaluate(Vector2d p);
    Vector2d gradient(Vector2d p);

}
