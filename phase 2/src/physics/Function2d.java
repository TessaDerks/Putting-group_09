package physics;

public interface Function2d {
    double delta = 1e-10;
    String masterFunction = null;
    double cap = 900;

    /**
     *
     * @param p
     * @return
     */
    double evaluate(Vector2d p);

    /**
     *
     * @param p
     * @return
     */
    Vector2d gradient(Vector2d p);

}
