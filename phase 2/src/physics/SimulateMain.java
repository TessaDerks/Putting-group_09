package physics;

import javafx.scene.control.Alert;
import main.Main;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
    private static String heightMap;


    // receives information about new terrain and sends information to the right classes
    public static void beginning(double _g, double _m, double _mu, double _vmax, double _tol, Vector2d _start, Vector2d _goal, String _height, int _version, @NotNull ArrayList<Vector2d> treePositions, ArrayList<Vector2d> sandPositions, String _heightMap) throws IOException {

        g = _g;
        m = _m;
        mu = _mu;
        vmax = _vmax;
        tol = _tol;
        start = _start;
        flag = _goal;
        if(SimulateMain.getHeightMap().equals("")){
            function = new Function(_height, 900);
        }
        else{
            function = new HeightMap(_heightMap);
        }

        version = _version;
        heightMap = _heightMap;


        // Create PuttingSimulator and set all given settings
        PuttingCourse course = new PuttingCourse(function, flag, start);
        course.set_mu(mu);
        course.set_vMax(vmax);
        course.set_holeTolerance(tol);


        // loop to add tree objects to puttingcourse
        for(int i = 0; i< treePositions.size(); i++){
            Tree t = new Tree(treePositions.get(i), 0.6);
            course.addTree(t);
        }


        String imagePath = "res/blendMapOriginal.png";
        BufferedImage blendMap = ImageIO.read(new File(imagePath));
        Graphics2D graphics = (Graphics2D) blendMap.getGraphics();
        graphics.setColor(Color.BLUE);

        for(int i = 0; i< sandPositions.size(); i = i+2){
            Sand s = new Sand(sandPositions.get(i), sandPositions.get(i+1), 0.8);
            course.addSand(s);

            graphics.fillRect((int)sandPositions.get(i).get_x(),(int) sandPositions.get(i).get_y(), (int) sandPositions.get(i+1).get_x(),(int) sandPositions.get(i+1).get_y());
        }
        ImageIO.write(blendMap, "png", new File("res/blendMap.png"));



        // create physics engine and set all settings for terrain
        PhysicsEngine engine = new SIESolver(start);
        engine.set_step_size(0.01);
        engine.set_h(function);
        engine.set_m(m);
        engine.set_g(g);
        engine.set_v_max(vmax);
        simulator = new PuttingSimulator(course, engine);
        if(function.evaluate(start)<0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Starting position not possible, please enter new position");

            alert.showAndWait();
        }
        else if(function.evaluate(flag)<0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Goal position not possible, please enter new position");

            alert.showAndWait();
        }
        else if(!Tools.checkGoalSlope(flag, function, m, g, mu)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Goal position not possible, too steep for ball to stop");

            alert.showAndWait();
        }
        else {
            simulator.set_ball_position(start);

            System.out.println("=== STARTING GAME ===");
            new Main().start();
        }
    }

    public static boolean beginning(double _g, double _m, double _mu, double _vmax, double _tol, Vector2d _start, Vector2d _goal, String _height) {

        g = _g;
        m = _m;
        mu = _mu;
        vmax = _vmax;
        tol = _tol;
        start = _start;
        flag = _goal;
        function = new Function(_height);

        if(Tools.checkGoalSlope(flag, function, m, g, mu)){
            // Create PuttingSimulator and set all given settings
            PuttingCourse course = new PuttingCourse(function, flag, start);
            course.set_mu(mu);
            course.set_vMax(vmax);
            course.set_holeTolerance(tol);

            // create physics engine and set all settings for terrain
            PhysicsEngine engine = new SIESolver(start);
            engine.set_step_size(0.01);
            engine.set_h(function);
            engine.set_m(m);
            engine.set_g(g);
            engine.set_v_max(vmax);
            simulator = new PuttingSimulator(course, engine);

            simulator.set_ball_position(start);
            return true;
        }
        else{
            return false;
        }



    }
    public static void start(){
        simulator.last_ball_position = start;
    }

    public static Function getFunction() {
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

    public static double getSpeed(){ return vmax; }

    public static String getHeightMap() {
        return heightMap;
    }


}