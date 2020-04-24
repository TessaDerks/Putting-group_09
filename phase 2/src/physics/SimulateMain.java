package physics;

import java.util.Scanner;

public class SimulateMain {
    private static Vector2d start;
    private static Vector2d flag;
    private static Function2d function;
    public static PuttingSimulator simulator;

    public static Function2d getFunction() {
        return function;
    }

    private static double g;
    private static double m;

    public static Vector2d getStart() {
        return start;
    }

    public static Vector2d getFlag() {
        return flag;
    }

    private static double mu;
    private static double vmax;
    private static double tol;

    public static boolean win = false;
    public static boolean shotsByFile = false;
    public static Vector2d[] shots;
    public static int shotArrayCount = 0;

    public static void beginning(/*double _g, double _m, double _mu, double _vmax, double _tol, Vector2d _start, Vector2d _goal, String _height*/) {

        //start = _start;
        //flag = _goal;
        //function = new Function2d(_height);

        initialInput();

      //  What.ball = start;
      //  What.flag = flag;

        // Create PuttingSimulator.
        PuttingCourse course = new PuttingCourse(function, flag, start);
        course.set_mu(mu);
        course.set_vMax(vmax);
        course.set_holeTolerance(tol);

        PhysicsEngine engine = new VerletSolver(start);
        engine.set_step_size(0.01);
        engine.set_h(function);
        engine.set_m(m);
        engine.set_g(g);
        engine.set_v_max(vmax);
        simulator = new PuttingSimulator(course, engine);
        simulator.set_ball_position(start);

        System.out.println("=== STARTING GAME ===");


    }

    public static void start(){
        simulator.last_ball_position = start;
        //simulator.set_ball_position(start);
        simulator.get_engine().sendPosition();

        // Start game.
        shoot();
        if(simulator.calcWin()){
            // Win.
            System.out.println();
            System.out.println("=!= Y O U   W I N =!=");
            System.out.println();

            win = true;
        }


    }

    private static void shoot(){
        Scanner in = new Scanner(System.in);

        if(shotsByFile){
            System.out.println("Press enter to take shot!");
            in.nextLine();
            System.out.println();
            System.out.println("=== S H O T   T A K E N ===");
            System.out.println();

            simulator.take_shot(shots[shotArrayCount]);
            shotArrayCount++;
        }
        else {
            System.out.println("Enter angle (0-360):");
            double angle = in.nextDouble();
            System.out.println("Enter velocity (max " + simulator.get_course().get_maximum_velocity() + "):");
            double v = in.nextDouble();


            System.out.println();
            System.out.println("=== S H O T   T A K E N ===");
            System.out.println();

            simulator.take_shot(Tools.velFromAngle(angle, v));
        }

    }

    private static void initialInput(){

        int shotType;
        double sx;
        double sy;
        double fx;
        double fy;

        Scanner in = new Scanner(System.in);
        System.out.println("Auto? (1)");
        int auto = in.nextInt();
        if(auto != 1) {
            System.out.println("Shots by (1)hand or (2)file");
            shotType = in.nextInt();
            System.out.println("Enter starting x:");
            sx = in.nextDouble();
            System.out.println("Enter starting y:");
            sy = in.nextDouble();
            System.out.println("Enter flag x:");
            fx = in.nextDouble();
            System.out.println("Enter flag y:");
            fy = in.nextDouble();
            System.out.println("Enter constant g:");
            g = in.nextDouble();
            System.out.println("Enter mass m:");
            m = in.nextDouble();
            System.out.println("Enter constant mu:");
            mu = in.nextDouble();
            System.out.println("Enter maximal velocity vmax:");
            vmax = in.nextDouble();
            System.out.println("Enter hole tolerance:");
            tol = in.nextDouble();
            in.nextLine();
            System.out.println("Function for landscape (spaced):");
            function = new Function2d(in.nextLine());
        }
        else{
            shotType = 1;
            sx = 300;
            sy = 300;
            fx = 306;
            fy = 306;
            g = 9.81;
            m = 45;
            mu = 0.131;
            vmax = 5;
            tol = 0.2;
            function = new Function2d("1");
        }

        start = new Vector2d(sx,sy);
        flag = new Vector2d(fx,fy);

        if(shotType == 2){
            shotsByFile = true;
        }

        if(shotsByFile){
            System.out.println("Shots-file name:");
            String filename = in.nextLine();
            shots = FileReader.playShot(filename);
        }
    }
}
