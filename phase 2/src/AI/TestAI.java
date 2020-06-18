package AI;

import main.Main;
import mazeAI.*;
import physics.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static mazeAI.Test.cToId;

public class TestAI {

    private static Vector2d start;
    private static Vector2d goal;
    private static List<Shot> botShots = new ArrayList<Shot>();
    private static  Vector2d currentStart;
    public static int shotCount = 0;

    public static List<Shot> getBotShots() {
        return botShots;
    }

    public static void main(String []args){
        // initialize terrain
        setStart();

        SimulateMain.simulator.get_course().addTree(new Tree(new Vector2d(8,8),3));

        /*
        int popSize = 25;
        Genetic.testCase = true;
        Tools tools = new Tools();
        //tools.adjustFlagPosition(start, goal);
        SimulateGenetic.initialize(popSize);
        Genetic.testCase = false;
         */

        GenerateNodes generator = new GenerateNodes(start,goal,0.5,3);
        RouteFinder finder = new RouteFinder(generator.getMaze());
        List<CheckPoint> route = finder.findRoute(generator.getMaze().getNode(cToId(start)),generator.getMaze().getNode(cToId(goal)));

        System.out.println();
        System.out.println(route.stream().map(CheckPoint::getId).collect(Collectors.toList()));

        RouteDivider divider = new RouteDivider(route,0.5);

        List<Shot> shots = divider.getShots();

        for(Shot s : shots){
            System.out.print(s);
        }
        System.out.println();
        System.out.println();

        currentStart = shots.get(0).getStart();

        for(Shot s : shots){
            Genetic.testCase = true;
            SimulateGenetic.initialize(20,currentStart,s.getEnd());
            Genetic.testCase = false;
            SimulateMain.simulator.get_course().set_holeTolerance(SimulateMain.simulator.get_course().get_hole_tolerance()*1.5);
            currentStart = SimulateGenetic.getLastEnd();
        }

        System.out.println("Done");

        System.out.println();

        for(Shot s : botShots){
            System.out.print(s.altToString());
        }
    }

    public static void takeAIShot(){

        SimulateMain.simulator.take_shot((Tools.velFromAngle(botShots.get(shotCount).getAngle(),botShots.get(shotCount).getSpeed())), false);
        Main.takingShot = true;
        shotCount++;

    }

    public static void runMazeAI(Vector2d _start, Vector2d _end){
        start = _start;
        goal = _end;
        Main.aiRunning = true;
        GenerateNodes generator = new GenerateNodes(start,goal,0.5,3);
        RouteFinder finder = new RouteFinder(generator.getMaze());
        List<CheckPoint> route = finder.findRoute(generator.getMaze().getNode(cToId(start)),generator.getMaze().getNode(cToId(goal)));

        System.out.println();
        System.out.println(route.stream().map(CheckPoint::getId).collect(Collectors.toList()));

        RouteDivider divider = new RouteDivider(route,0.5);

        List<Shot> shots = divider.getShots();

        for(Shot s : shots){
            System.out.print(s);
        }
        System.out.println();
        System.out.println();

        currentStart = shots.get(0).getStart();

        for(Shot s : shots){
            Genetic.testCase = true;
            SimulateGenetic.initialize(20,currentStart,s.getEnd());
            Genetic.testCase = false;
            SimulateMain.simulator.get_course().set_holeTolerance(SimulateMain.simulator.get_course().get_hole_tolerance()*1);
            currentStart = SimulateGenetic.getLastEnd();
        }

        SimulateMain.simulator.get_engine().resetPosition(start);
        SimulateMain.simulator.set_ball_position(start);
        System.out.println(start);

        System.out.println("Done");

        System.out.println();

        takeAIShot();

        System.out.println(SimulateGenetic.getLastEnd());

        System.out.println(SimulateMain.getFlag().toString());
    }

    public static void setStart(){
        double g = 9.81;
        double mass = 45;
        double mu = 0.131;
        double maxV = 60;
        double radius = 0.2;
        String height = "1";
        SimulateMain.beginning(g, mass, mu, maxV, radius, start, goal, height);
    }

    public static void addBotShots(Shot s){
        botShots.add(s);
    }
}
