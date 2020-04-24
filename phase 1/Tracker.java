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

    public Tracker(Vectro2d _p, Vector2d _v){
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
        double newVX = v.getX() + dt * F.getX() / m;
        double newVY = v.getY() + dt * F.getY() / m;

        p = new Vector2d(newPX,newPY);
        v = new Vector2d(newVX,newVY);

        recalculate();

        t += dt;
    }

    public Vector2d get_p(){
        return p;
    }

    public Vector2d get_v(){
        return v;
    }
}
