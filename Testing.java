import java.util.Scanner;

public class Testing {
    public static Vector2d startingPos;
    public static Vector2d startingVel;

    public static void main(String[] args){
        input();
        Tracker tracker = new Tracker(startingPos,startingVel);

        // While ball is still moving.
        while(tracker.get_v().getX() != 0 || tracker.get_v().getY() != 0){
            tracker.actTimestep(0.01);

            // Printing data.
            Vector2d cp = tracker.get_p();
            Vector2d cv = tracker.get_v();
            System.out.println("t=" + tracker.get_t() + ": p(" + cp.getX() + "," + cp.getY() + ") v(" + cv.getX() + "," + cv.getY() + ")");
        }
    }

    public static void input(){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter starting x");
        double startX = in.nextDouble();
        System.out.println("Enter starting y");
        double startY = in.nextDouble();
        System.out.println("Enter starting angle (0-360):");
        double startAngle = in.nextDouble();
        System.out.println("Enter starting velocity (m/s):");
        double startVel = in.nextDouble();

        startingPos = new Vector2d(startX,startY);
        startingVel = velFromAngle(startAngle,startVel);
    }

    public static Vector2d velFromAngle(double a, double v){
        a = Math.toRadians(a);
        double vy = v * Math.cos(a);
        double vx = Math.sqrt(Math.pow(v,2) - Math.pow(vy,2));
        return new Vector2d(vx,vy);
    }
}
