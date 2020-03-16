import java.util.Scanner;
import java.util.ArrayList;

public class PuttingSimulator {
    private static Vector2d ballPosition;
    private static Vector2d lastPosition;
    private static Vector2d ballVel;
    private static int roundCount = 1;
    private static double winRadius = 0.02;
    private static Vector2d goalPosition = new Vector2d(6,0);
    private static ArrayList<Tree> trees = new ArrayList<>();
    private static double lastAngle;


    public static void main(String[] args){

        // Place trees
        trees.add(new Tree(new Vector2d(1,1),0.5));
        trees.add(new Tree(new Vector2d(2,1),0.5));
        trees.add(new Tree(new Vector2d(0,1),0.5));

        firstInput();
        lastPosition = ballPosition;
        take_shot.take_shot(ballPosition,ballVel);

        while(true){
            System.out.println("p(" + ballPosition.getX() + "," + ballPosition.getY() + ")");
            roundInput();
            lastPosition = ballPosition;
            take_shot.take_shot(ballPosition,ballVel);
            roundCount++;

            // If ball is in goal.
            if(calcWin()){
                System.out.println("Goal reached");
            }
        }
    }

    public static void firstInput(){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter starting x");
        double startX = in.nextDouble();
        System.out.println("Enter starting y");
        double startY = in.nextDouble();
        System.out.println("Enter starting angle (0-360):");
        double startAngle = in.nextDouble();
        System.out.println("Enter starting velocity (m/s):");
        double startVel = in.nextDouble();

        lastAngle = startAngle;
        ballPosition = new Vector2d(startX,startY);
        ballVel = velFromAngle(startAngle,startVel);

    }

    public static void roundInput(){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter starting angle (0-360):");
        double startAngle = in.nextDouble();
        System.out.println("Enter starting velocity (m/s):");
        double startVel = in.nextDouble();

        lastAngle = startAngle;
        ballVel = velFromAngle(startAngle,startVel);
    }

    public static Vector2d velFromAngle(double a, double v){
        a = Math.toRadians(a);
        double vy = v * Math.cos(a);
        double vx = v * Math.sin(a);
        return new Vector2d(vx,vy);
    }

    public static void setBallPosition(Vector2d _ballPosition){
        ballPosition = _ballPosition;
    }

    public static Vector2d getBallPosition(){
        return ballPosition;
    }

    public static Vector2d getLastPosition(){
        return lastPosition;
    }

    public static boolean calcWin(){
        boolean r = false;
        double absDis = Math.sqrt(Math.pow(ballPosition.getX() - goalPosition.getX(),2) + Math.pow(ballPosition.getY() - goalPosition.getY(),2));
        if(absDis < winRadius){
            r = true;
        }
        return r;
    }

    public static ArrayList<Tree> getTrees() {
        return trees;
    }

    public static double getLastAngle(){
        return lastAngle;
    }
}
