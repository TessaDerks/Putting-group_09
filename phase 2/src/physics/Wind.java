package physics;

/**
 * @author Stijn Hennissen
 */
public class Wind {
    private double angle;
    private double totalForce;
    private Vector2d force;

    /**
     * Constructor, random force and direction.
     */
    public Wind(){
        angle = Math.random() * 360;
        double b = 30;// Upper bound at 30 m/s -> https://en.wikipedia.org/wiki/Beaufort_scale
        totalForce = calcBoundedForce(0,b);
        double f = 0.5*1.225*Math.pow(totalForce,2)*(Math.pow(0.043,2)*Math.PI);
        force = Tools.velFromAngle(angle,f);
    }

    /**
     * Constructor, random force and direction, with bounds.
     * @param angleLowerBound double, angleLowerBound < angleUpperBound
     * @param angleUpperBound double, angleUpperBound > angleLowerBound
     * @param forceLowerBound double, forceLowerBound < forceUpperBound
     * @param forceUpperBound double, forceUpperBound > forceLowerBound
     */
    public Wind(double angleLowerBound, double angleUpperBound, double forceLowerBound, double forceUpperBound){
        angle = Math.random() * (angleUpperBound-angleLowerBound) + angleLowerBound;
        totalForce = calcBoundedForce(forceLowerBound,forceUpperBound);
        double f = 0.5*1.225*Math.pow(totalForce,2)*(Math.pow(0.043,2)*Math.PI);
        force = Tools.velFromAngle(angle,f);
    }

    /**
     * Constructor, chosen force and direction.
     * @param _angle double
     * @param _totalForce double
     */
    public Wind(double _angle, double _totalForce){
        angle = _angle;
        totalForce = _totalForce;
        double f = 0.5*1.225*Math.pow(totalForce,2)*(Math.pow(0.043,2)*Math.PI);
        force = Tools.velFromAngle(angle,f);
    }

    /**
     * Calculate a force roughly following frequency of wind speed occurrence, bounded.
     * @param lB double, lB < uB
     * @param uB double, uB > lB
     * @return double, force, lB <= force <= uB
     */
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

    public Vector2d getForce() {
        return force;
    }

    //</editor-fold>
}
