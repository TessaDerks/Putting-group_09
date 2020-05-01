package com.project.puttingsimulator;

import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.mygdx.puttingame.*;

public class SimulateMain {
    private static Vector2d start;
    private static Vector2d flag;
    private static Function2d function;
    public static PuttingSimulator simulator;

    private static double g;
    private static double m;
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

        What.ball = start;
        What.flag = flag;

        // Create PuttingSimulator.
        PuttingCourse course = new PuttingCourse(function, flag, start);
        course.set_mu(mu);
        course.set_vMax(vmax);
        course.set_holeTolerance(tol);

        EulerSolver engine = new EulerSolver(start);
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
        Scanner in = new Scanner(System.in);
        System.out.println("Shots by (1)hand or (2)file");
        int shotType = in.nextInt();
        System.out.println("Enter starting x:");
        double sx = -in.nextDouble();
        System.out.println("Enter starting y:");
        double sy = -in.nextDouble();
        System.out.println("Enter flag x:");
        double fx = -in.nextDouble();
        System.out.println("Enter flag y:");
        double fy = -in.nextDouble();
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
