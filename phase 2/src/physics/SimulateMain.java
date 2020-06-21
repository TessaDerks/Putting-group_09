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

/**
 * @author Stijn Hennissen
 */
public class SimulateMain {

    private static Vector2d start;
    private static Vector2d flag;
    private static Function2d function;
    public static PuttingSimulator simulator;
    public static int version;
    private static double vmax;
    private static String heightMap;

    /**
     * set all variables for terrain and physics engine
     * @param _g double, gravitation force
     * @param _m double, mass of ball
     * @param _mu double, friction coefficients
     * @param _vmax double, maximum speed for golf ball
     * @param _tol double, radius around hole that golf ball has to eventually land in
     * @param _start Vector2d, starting position for golf ball
     * @param _goal Vector2d, position of hole
     * @param _height String, function to calculate height for terrein
     * @param _version int, determines manual of filereader for input
     * @param _treePositions ArrayList<Vector2d>, list of positions of trees
     * @param _sandPositions ArrayList<Vector2d>, list of position of sand spaces
     * @param _heightMap String, name of file with heightmap to describe terrain
     * @param _stumps ArrayList<Tree>, list of stumps for maze
     * @throws IOException
     */
    public static void beginning(double _g, double _m, double _mu, double _vmax, double _tol, Vector2d _start, Vector2d _goal, String _height, int _version, @NotNull ArrayList<Vector2d> _treePositions, ArrayList<Vector2d> _sandPositions, String _heightMap, ArrayList<Tree> _stumps) throws IOException {

        vmax = _vmax;
        start = _start;
        flag = _goal;
        version = _version;
        heightMap = _heightMap;

        // create function based on input
        if(heightMap.equals("")){
            function = new Function(_height, 900);
        }
        else{
            function = new HeightMap(heightMap);
        }

        // Create PuttingSimulator and set all given settings
        PuttingCourse course = new PuttingCourse(function, flag, start);
        course.set_mu(_mu);
        course.set_vMax(vmax);
        course.set_holeTolerance(_tol);


        // add tree objects to puttingcourse
        for (Vector2d treePosition : _treePositions) {
            Tree t = new Tree(treePosition, 0.5);
            course.addTree(t);
        }

        for (Tree stump : _stumps) {
            course.addStump(stump);
        }

        // add sand to picture describing terrain
        String imagePath = "res/blendMapOriginal.png";
        BufferedImage blendMap = ImageIO.read(new File(imagePath));
        Graphics2D graphics = (Graphics2D) blendMap.getGraphics();
        graphics.setColor(Color.BLUE);

        for(int i = 0; i< _sandPositions.size(); i = i+2){
            Sand s = new Sand(_sandPositions.get(i), _sandPositions.get(i+1), 0.8);
            course.addSand(s);

            graphics.fillRect((int)_sandPositions.get(i).get_x(),(int) _sandPositions.get(i).get_y(), (int) _sandPositions.get(i+1).get_x(),(int) _sandPositions.get(i+1).get_y());
        }
        ImageIO.write(blendMap, "png", new File("res/blendMap.png"));

        // create physics engine and set all variables
        PhysicsEngine engine = new SIESolver(start);
        engine.set_step_size(0.01);
        engine.set_h(function);
        engine.set_m(_m);
        engine.set_g(_g);
        engine.set_v_max(vmax);
        simulator = new PuttingSimulator(course, engine);

        // check if starting settings are possible otherwise start game
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
        else if(!Tools.checkGoalSlope(flag, function, _m, _g, _mu)){
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


    public static void start(){
        simulator.last_ball_position = start;
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

    public static double getSpeed(){ return vmax; }

    public static String getHeightMap() {
        return heightMap;
    }

}