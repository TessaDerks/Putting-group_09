package physics;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

/**
 * @author Stijn Hennissen
 */
public class Tools {

    /**
     * Round a number to a certain amount of decimal places.
     * @param num double, number to be rounded
     * @param dec int, amount of decimal places to round 'num' to
     * @return double, 'num' rounded to 'dec' decimal places
     */
    public static double advRound(double num, int dec){
        num = num * Math.pow(10,dec);
        num = Math.round(num);
        num = num / Math.pow(10,dec);
        return num;
    }

    /**
     * Get Vector2d velocity form angle and total velocity.
     * @param a double, angle in degrees
     * @param v double, velocity in m/s
     * @return Vector2d, speed acting in x and y direction
     */
    @Contract("_, _ -> new")
    public static @NotNull Vector2d velFromAngle(double a, double v){
        a = Math.toRadians(a);
        double vy = v * Math.cos(a);
        double vx = v * Math.sin(a);
        return new Vector2d(vx,vy);
    }

    /**
     * Pause program for x milliseconds
     * @param time int, milliseconds
     */
    public static void wait(int time){
        try{
            TimeUnit.MILLISECONDS.sleep(time);}
        catch(InterruptedException ignored){}
    }

    /**
     * Method to adjust the flag position to the left to make a checker flag
     * @param start Vector2d, starting position
     * @param flag Vector2d, flag position
     * @return Vector2d, checker flag used to determine if ball is on left or right side of actual flag
     */
    @Contract("_, _ -> new")
    public static @NotNull Vector2d adjustFlagPosition(@NotNull Vector2d start, @NotNull Vector2d flag) {
        double angleFlag = Math.atan((flag.get_y() - start.get_y()) / (flag.get_x() - start.get_x()));
        angleFlag = Math.toDegrees(angleFlag);
        double magnitude = Math.sqrt(Math.pow(flag.get_x() - start.get_x(), 2) + Math.pow(flag.get_y() - start.get_y(), 2));
        double angleChecker = angleFlag + 0.01;
        Vector2d temp = velFromAngle(angleChecker, magnitude);
        if (start.get_x() <= flag.get_x() || start.get_y() <= flag.get_y()) {
            return new Vector2d(temp.get_y() + start.get_x(), temp.get_x() + start.get_y());
        }
        else {
            return new Vector2d(Math.abs(temp.get_y() - start.get_x()), Math.abs(temp.get_x() - start.get_y()));
        }

    }

    /**
     * Method to check if goal is to vertical.
     * @param goal_position
     * @param function
     * @param mass
     * @param g
     * @param mu
     * @return
     */
    public static boolean checkGoalSlope(Vector2d goal_position, Function2d function, double mass, double g, double mu){
        boolean result = true;
        Vector2d G = calcG(function,goal_position,mass,g);
        Vector2d H = calcH(mu,mass/1000,g,G);
        if((Math.abs(H.get_x())<G.get_x())||(Math.abs(H.get_y())<G.get_y()))
        {
            result = false;
        }
        return result;
    }

    /**
     *
     * @param h
     * @param p
     * @param m
     * @param g
     * @return
     */
    public static @NotNull Vector2d calcG(@NotNull Function2d h, Vector2d p, double m, double g){
        Vector2d der = h.gradient(p);
        return new Vector2d(-m*g*der.get_x(),-m*g*der.get_y());
    }

    /**
     *
     * @param mu
     * @param m
     * @param g
     * @param v
     * @return
     */
    @Contract("_, _, _, _ -> new")
    public static @NotNull Vector2d calcH(double mu, double m, double g, @NotNull Vector2d v){
        double xH = (-mu * m * g * v.get_x());
        double yH = (-mu * m * g * v.get_y());
        return new Vector2d(xH,yH);
    }


}
