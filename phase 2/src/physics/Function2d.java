package physics;

public interface Function2d {
    final double delta = 1e-10;
    String masterFunction = null;
    double cap = 900;

    double evaluate(Vector2d p);
    Vector2d gradient(Vector2d p);

}
