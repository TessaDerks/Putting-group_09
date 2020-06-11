package physics;

public class Wind {
    private double angle;
    private double totalForce;
    private Vector2d force;

    // Random force and direction.
    public Wind(){
        angle = Math.random() * 360;
        double b = 30;// Upper bound at 30 m/s -> https://en.wikipedia.org/wiki/Beaufort_scale
        totalForce = calcBoundedForce(0,b);
        force = Tools.velFromAngle(angle,totalForce);
    }

    // Random force and direction, with bounds.
    public Wind(double angleLowerBound, double angleUpperBound, double forceLowerBound, double forceUpperBound){
        angle = Math.random() * (angleUpperBound-angleLowerBound) + angleLowerBound;
        totalForce = calcBoundedForce(forceLowerBound,forceUpperBound);
    }

    // Chosen force and direction.
    public Wind(double _angle, double _totalForce){
        angle = _angle;
        totalForce = _totalForce;
        force = Tools.velFromAngle(angle,totalForce);
    }

    // Calculate a force roughly following frequency of wind speed occurrence, bounded.
    private double calcBoundedForce(double lB, double uB){
        int i = 0;
        double d = uB-lB;
        double f = Math.random() * d + lB;
        while(d > 1){
            d = d/2;
            if(i % 2 == 0){
                f -= Math.random() * d;
            }
            else{
                f += Math.random() * d;
            }
            i++;
        }
        return f;
    }

    //<editor-fold desc="Getters">

    public double getAngle() {
        return angle;
    }

    public double getTotalForce() {
        return totalForce;
    }

    public Vector2d getForce() {
        return force;
    }

    //</editor-fold>
}
