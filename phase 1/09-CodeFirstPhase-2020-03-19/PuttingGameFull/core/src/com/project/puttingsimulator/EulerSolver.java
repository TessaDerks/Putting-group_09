package com.project.puttingsimulator;

import com.mygdx.puttingame.*;

public class EulerSolver {
    private double t = 0;
    private double m = 45.93;
    private double g = 9.81;
    private double mu;
    private double dt;
    private double v_max;
    private Vector2d p;
    private Vector2d v;
    private Function2d h;
    private Vector2d G;
    private Vector2d H;
    private Vector2d F;

    public EulerSolver(Vector2d _p){
        p = _p;
    }

    public void actTimestep(){
        recalculate();

        double newPX = p.get_x() + dt * v.get_x();
        double newPY = p.get_y() + dt * v.get_y();
        double newVX = Tools.advRound(v.get_x() + dt * F.get_x() / m,2);
        double newVY = Tools.advRound(v.get_y() + dt * F.get_y() / m,2);

        p = new Vector2d(newPX,newPY);
        v = new Vector2d(newVX,newVY);

        recalculate();

        t = Tools.advRound(t+dt,6);

        //Tools.wait((int)(dt * 1000));
        sendPosition();

    }

    public void sendPosition(){
        What.ent.setPosition((float) p.get_x(),(float) (h.evaluate(new Vector2d(p.get_x(),p.get_y()))+0.15),(float) p.get_y());
    }

    // Calculators.
    public void recalculate(){
        G = calcG();
        H = calcH();
        F = calcF();
    }

    private Vector2d calcG(){
        Vector2d der = h.gradient(p);
        return new Vector2d(-m*g*der.get_x(),-m*g*der.get_y());
    }

    private Vector2d calcH(){
        double xH = (-mu * m * g * v.get_x()) / Math.sqrt(Math.pow(v.get_x(),2) + Math.pow(v.get_y(),2));
        double yH = (-mu * m * g * v.get_y()) / Math.sqrt(Math.pow(v.get_x(),2) + Math.pow(v.get_y(),2));
        return new Vector2d(xH,yH);
    }

    private Vector2d calcF(){
        return new Vector2d(G.get_x()+H.get_x(),G.get_y()+H.get_y());
    }

    // Setters.
    public void setMu(double _mu){
        mu = _mu;
    }

    public void set_m(double _m){
        m = _m;
    }

    public void set_g(double _g){
        g = _g;
    }

    public void set_v(Vector2d _v){
        double v_com = Math.sqrt(Math.pow(_v.get_x(),2) + Math.pow(_v.get_y(),2));
        if(v_com > v_max){
            v = new Vector2d(0,0);
        }
        else{
            v = _v;
        }
    }

    public void set_step_size(double _dt){
        dt = _dt;
    }

    public void set_h(Function2d _h){
        h = _h;
    }

    public void set_v_max(double _v_max){
        v_max = _v_max;
    }

    // Getters.
    public Vector2d get_p(){
        return p;
    }

    public Vector2d get_v(){
        return v;
    }

    public double get_t(){
        return t;
    }

    public Function2d get_h(){
        return h;
    }
}
