package physics;

public interface PhysicsEngine {
    void actTimestep();
    public void recalculate();

    /**
     * 
     * @param start
     */
    void resetPosition(Vector2d start);

    /**
     *
     * @return
     */
    Vector2d calcG();

    /**
     *
     * @return
     */
    Vector2d calcH();

    /**
     * 
     * @return
     */
    Vector2d calcF();

    void setMu(double _mu);
    void set_m(double _m);
    void set_g(double _g);
    void set_v(Vector2d _v);
    void set_h(Function2d _h);
    void set_v_max(double _v_max);
    void set_step_size(double _dt);
    void set_t(double _t);

    Function2d get_h();
    Vector2d get_p();
    Vector2d get_v();
    double get_t();
}
