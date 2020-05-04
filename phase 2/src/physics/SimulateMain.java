package physics;

import main.Main;

public class SimulateMain {
    private static Vector2d start;
    private static Vector2d flag;
    private static Function2d function;
    public static PuttingSimulator simulator;
    public static int version;
    private static double g;
    private static double m;
    private static double mu;
    private static double vmax;
    private static double tol;

   

    // receives information about new terrain and sends information to the right classes
    public static void beginning(double _g, double _m, double _mu, double _vmax, double _tol, Vector2d _start, Vector2d _goal, String _height, int _version) {

        g = _g;
        m = _m;
        mu = _mu;
        vmax = _vmax;
        tol = _tol;
        start = _start;
        flag = _goal;
        function = new Function2d(_height);
        version = _version;


        // Create PuttingSimulator and set all given settings 
        PuttingCourse course = new PuttingCourse(function, flag, start);
        course.set_mu(mu);
        course.set_vMax(vmax);
        course.set_holeTolerance(tol);

        // create physics engine and set all settings for terrain
        PhysicsEngine engine = new VerletSolver( start);
        engine.set_step_size(0.01);
        engine.set_h(function);
        engine.set_m(m);
        engine.set_g(g);
        engine.set_v_max(vmax);
        simulator = new PuttingSimulator(course, engine);
        simulator.set_ball_position(start);

        System.out.println("=== STARTING GAME ===");
        new Main().start();

    }

    public static void start(){
        simulator.last_ball_position = start;
        simulator.get_engine().sendPosition();
    }

    public static Function2d getFunction() {
        return function;
    }

    public static Vector2d getStart() {
        return start;
    }

    public static Vector2d getFlag() {
        return flag;
    }


    public static int getVersion() {
        return version;
    }

   

}
