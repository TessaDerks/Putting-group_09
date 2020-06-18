package mazeAI;

import physics.Vector2d;

public class Shot {
    private Vector2d start;
    private Vector2d end;
    private double speed;
    private double angle;

    /**
     *
     * @param _start
     * @param _end
     */
    public Shot(Vector2d _start, Vector2d _end){
        start = _start;
        end = _end;
    }

    /**
     *
     * @param _angle
     * @param _speed
     */
    public Shot(double _angle, double _speed){
        angle = _angle;
        speed = _speed;
    }

    @Override
    public String toString() {
        return "Shot{"+start.toString()+end.toString()+"}";
    }

    /**
     *
     * @return
     */
    public String altToString(){
        return "Shot{a:"+angle+";s:"+speed+"}";
    }

    public double getAngle() {
        return angle;
    }

    public double getSpeed() {
        return speed;
    }

    public Vector2d getEnd() {
        return end;
    }

    public Vector2d getStart() {
        return start;
    }
}
