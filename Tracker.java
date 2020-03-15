public class Tracker {
    private double t = 0;
    private double m = 45.93;
    private double g = 9.81;
    private double mu = 0.131;
    private Vector2d p;
    private Vector2d v;
    private Vector2d h;
    private Vector2d G;
    private Vector2d H;
    private Vector2d F;

    public Tracker(Vector2d _p, Vector2d _v){
        p = _p;
        v = _v;
        h = get_h.get_h(p.getX(),p.getY());
        G = calcG();
        H = calcH();
        F = calcF();
    }

    private Vector2d calcG(){
        return new Vector2d(-m*g*h.getX(),-m*g*h.getY());
    }

    private Vector2d calcH(){
        double xH = (-mu * m * g * v.getX()) / Math.sqrt(Math.pow(v.getX(),2) + Math.pow(v.getY(),2));
        double yH = (-mu * m * g * v.getY()) / Math.sqrt(Math.pow(v.getX(),2) + Math.pow(v.getY(),2));
        return new Vector2d(xH,yH);
    }

    private Vector2d calcF(){
        return new Vector2d(G.getX()+H.getX(),G.getY()+H.getY());
    }

    private void recalculate(){
        h = get_h.get_h(p.getX(),p.getY());
        G = calcG();
        H = calcH();
        F = calcF();
    }

    public void actTimestep(double dt){
        double newPX = p.getX() + dt * v.getX();
        double newPY = p.getY() + dt * v.getY();
        double newVX = get_h.advRound(v.getX() + dt * F.getX() / m,2);
        double newVY = get_h.advRound(v.getY() + dt * F.getY() / m,2);

        p = new Vector2d(newPX,newPY);
        v = new Vector2d(newVX,newVY);

        recalculate();

        t = get_h.advRound(t+dt,6);
    }

    public Vector2d get_p(){
        return p;
    }

    public Vector2d get_v(){
        return v;
    }

    public double get_t(){
        return t;
    }
}
