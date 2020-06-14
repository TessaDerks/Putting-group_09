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
    public static Vector2d adjustFlagPosition(Vector2d start, Vector2d flag){
        // new method to adjust the flag position to the left to make a checker flag
        Double returnX, returnY;
        if(((start.get_x() <= flag.get_x()) && (start.get_y() <= flag.get_y())) || ((start.get_x() >= flag.get_x()) && (start.get_y() >= flag.get_y()))){
            Double angle = Math.toDegrees(Math.atan(((Math.abs(flag.get_y()-start.get_y()))/(Math.abs(flag.get_x()-start.get_x()))))); // calculating angle
            System.out.println(angle);
            angle-=0.01;
            System.out.println(angle);
            Double absDis = Math.sqrt(Math.pow(flag.get_x()-start.get_x(),2)+Math.pow(flag.get_y()-start.get_y(),2));
            returnY = Math.abs(Math.sin(Math.toRadians(angle))*absDis);
            returnX = Math.abs(Math.cos(Math.toRadians(angle))*absDis);
        }
        else {
            Double angle = Math.toDegrees(Math.atan(((Math.abs(flag.get_y()-start.get_y()))/(Math.abs(flag.get_x()-start.get_x()))))); // calculating angle
            System.out.println(angle);
            angle+=0.01;
            System.out.println(angle);
            Double absDis = Math.sqrt(Math.pow(flag.get_x()-start.get_x(),2)+Math.pow(flag.get_y()-start.get_y(),2));
            returnY = Math.abs(Math.sin(Math.toRadians(angle))*absDis);
            returnX = Math.abs(Math.cos(Math.toRadians(angle))*absDis);
        }

        //System.out.println(returnX + " " + returnY );
        if (flag.get_y() < 0) {
            if (flag.get_x() > 0) return new Vector2d(returnX,-returnY);
            else return new Vector2d(-returnX, - returnY);
        }
        else if ((flag.get_y() > 0) &&( flag.get_x() < 0) ) return new Vector2d(-returnX, returnY);
        else return new Vector2d(returnX, returnY);

        //Vector2d adjusted = new Vector2d()
    }

    // Method to check if goal is to vertical.
    public static boolean checkGoalSlope(Vector2d goal_position,Function2d function, double mass, double g, double mu){
        boolean result = true;
        Vector2d G = calcG(function,goal_position,mass,g);
        Vector2d H = calcH(mu,mass/1000,g,G);

        System.out.println("G"+G.get_x()+" "+G.get_y());
        System.out.println("H"+H.get_x()+" "+H.get_y());

        if((Math.abs(H.get_x())<G.get_x())||(Math.abs(H.get_y())<G.get_y()))
        {
            result = false;
        }
        return result;
    }

    public static Vector2d calcG(Function2d h, Vector2d p, double m, double g){
        Vector2d der = h.gradient(p);
        Vector2d output = new Vector2d(-m*g*der.get_x(),-m*g*der.get_y());

        return output;
    }

    public static Vector2d calcH(double mu, double m, double g, Vector2d v){
        double xH = (-mu * m * g * v.get_x());
        double yH = (-mu * m * g * v.get_y());
        return new Vector2d(xH,yH);
    }


}
