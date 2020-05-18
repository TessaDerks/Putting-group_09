package physics;


public class EulerSolver implements PhysicsEngine{

    //<editor-fold desc="Global Variables">
    private double t = 0;
    private double m = 45.93;
    private double g = 9.81;
    private double mu = 0.131;
    private double dt = 0.01;
    private double v_max = 30;
    private Vector2d p;
    private Vector2d v;
    private Function2d h;
    private Vector2d G;
    private Vector2d H;
    private Vector2d F;
    //</editor-fold>

    public EulerSolver(Vector2d _p){
        p = _p;
    }

    @Override
    public void actTimestep(){
        recalculate();

        // Calculate new position and velocity.
        double newPX = p.get_x() + dt * v.get_x();
        double newPY = p.get_y() + dt * v.get_y();
        double newVX = v.get_x() + dt * F.get_x() / m;
        double newVY = v.get_y() + dt * F.get_y() / m;

        p = new Vector2d(newPX,newPY);
        v = new Vector2d(newVX,newVY);

        t = Tools.advRound(t+dt,6);
    }

    //<editor-fold desc="Calculators">
    public void resetPosition(Vector2d start){
        p = start;
        recalculate();
    }

    @Override
    public void recalculate(){
        G = calcG();
        H = calcH();
        F = calcF();
    }

    @Override
    public Vector2d calcG(){
        Vector2d der = h.gradient(p);
        Vector2d output = new Vector2d(-m*g*der.get_x(),-m*g*der.get_y());
        return output;
    }

    @Override
    public Vector2d calcH(){
        double xH = (-mu * m * g * v.get_x());
        double yH = (-mu * m * g * v.get_y());
        return new Vector2d(xH,yH);
    }

    @Override
    public Vector2d calcF(){
        return new Vector2d(G.get_x()+H.get_x(),G.get_y()+H.get_y());
    }
    //</editor-fold>

    //<editor-fold desc="Setters">
    @Override
    public void setMu(double _mu) {
        mu = _mu;
    }

    @Override
    public void set_m(double _m) {
        m = _m / 1000;
    }

    @Override
    public void set_g(double _g) {
        g = _g;
    }

    @Override
    public void set_v(Vector2d _v) {
        if(Math.sqrt(Math.pow(_v.get_x(),2)+Math.pow(_v.get_y(),2))>v_max)
        {
            v = new Vector2d(0,0);
        }
        else {
            v = _v;
        }
    }

    @Override
    public void set_h(Function2d _h) {
        h = _h;
    }

    @Override
    public void set_v_max(double _v_max) {
        v_max = _v_max;
    }

    @Override
    public void set_step_size(double _dt) {
        dt = _dt;
    }

    @Override
    public void set_t(double _t) {
        t = _t;
    }

    //</editor-fold>

    //<editor-fold desc="Getters">
    @Override
    public Function2d get_h() {
        return h;
    }

    @Override
    public Vector2d get_p() {
        return p;
    }

    @Override
    public Vector2d get_v() {
        return v;
    }

    public double get_t(){
        return t;
    }
    //</editor-fold>
}
