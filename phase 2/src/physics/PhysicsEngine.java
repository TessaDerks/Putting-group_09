package physics;

public interface PhysicsEngine {
    public void actTimestep();
    public void recalculate();
    public void resetPosition(Vector2d start);

    public Vector2d calcG();
    public Vector2d calcH();
    public Vector2d calcF();

    public void setMu(double _mu);
    public void set_m(double _m);
    public void set_g(double _g);
    public void set_v(Vector2d _v);
    public void set_h(Function _h);
    public void set_v_max(double _v_max);
    public void set_step_size(double _dt);
    public void set_t(double _t);

    public Function get_h();
    public Vector2d get_p();
    public Vector2d get_v();
    public double get_t();
    public double get_m();
}
