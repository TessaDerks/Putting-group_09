package mazeAI;

import physics.Vector2d;

public class Shot {
    private Vector2d start;
    private Vector2d end;
    private double speed;
    private double angle;

    /**
     *
     * @param _start Vector2d
     * @param _end Vector2d
     */
    public Shot(Vector2d _start, Vector2d _end){
        start = _start;
        end = _end;
    }

    /**
     *
     * @param _angle double, 0<= angle <= 360, angle to shoot the shot from one checkpoint to another
     * @param _speed double, 0 < speed < maxSpeed, speed to shoot that shot
     */
    public Shot(double _angle, double _speed){
        angle = _angle;
        speed = _speed;
    }

    /**
     *
     * @return String, including start and end point
     */
    @Override
    public String toString() {
        return "Shot{"+start.toString()+end.toString()+"}";
    }

    /**
     *
     * @return String, including angle and speed
     */

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
