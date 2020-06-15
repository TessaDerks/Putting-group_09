package mazeAI;

import physics.Vector2d;

public class Shot {
    private Vector2d start;
    private Vector2d end;
    private double speed;
    private double angle;

    public Shot(Vector2d _start, Vector2d _end){
        start = _start;
        end = _end;
    }

    public Shot(double _angle, double _speed){
        angle = _angle;
        speed = _speed;
    }
}
