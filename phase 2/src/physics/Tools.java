package physics;

import java.util.concurrent.TimeUnit;

public class Tools {

    // Round a number to a certain amount of decimal places.
    public static double advRound(double num, int dec){
        num = num * Math.pow(10,dec);
        num = Math.round(num);
        num = num / Math.pow(10,dec);
        return num;
    }

    // Get Vector2d velocity form angle and total velocity.
    public static Vector2d velFromAngle(double a, double v){
        a = Math.toRadians(a);
        double vy = v * Math.cos(a);
        double vx = v * Math.sin(a);
        return new Vector2d(vx,vy);
    }

    // Pause program for x milliseconds.
    public static void wait(int time){
        try{
            TimeUnit.MILLISECONDS.sleep(time);}
        catch(InterruptedException e){}
        return;
    }

    // Method to adjust the flag position to the left to make a checker flag
    public static Vector2d AdjustFlagPosition(Vector2d flag){
        // new method to adjust the flag position to the left to make a checker flag
        //Vector2d nullPoint = new Vector2d(0.0, 0.0);
        Double angle = Math.toDegrees(Math.atan(((Math.abs(flag.get_y()))/(Math.abs(flag.get_x()))))); // calculating angle
        //System.out.println(angle);
        angle+=0.01;
        Double absDis = Math.sqrt(Math.pow(flag.get_x(),2)+Math.pow(flag.get_y(),2));
        Double returnY = Math.abs(Math.sin(Math.toRadians(angle))*absDis);
        Double returnX = Math.abs(Math.cos(Math.toRadians(angle))*absDis);
        //System.out.println(returnX + " " + returnY );
        if (flag.get_y() < 0) {
            if (flag.get_x() > 0) return new Vector2d(returnX,-returnY);
            else return new Vector2d(-returnX, - returnY);
        }
        else if ((flag.get_y() > 0) &&( flag.get_x() < 0) ) return new Vector2d(-returnX, returnY);
        else return new Vector2d(returnX, returnY);

        //Vector2d adjusted = new Vector2d()
    }
}
