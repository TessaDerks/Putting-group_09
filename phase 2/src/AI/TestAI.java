package AI;

import mazeAI.*;
import physics.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static mazeAI.Test.cToId;

public class TestAI {

    private static Vector2d start = new Vector2d(0, 0);
    private static Vector2d goal = new Vector2d(12,12);
    private static List<Shot> botShots = new ArrayList<Shot>();
    private static  Vector2d currentStart;

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

        System.out.println(route.stream().map(CheckPoint::getId).collect(Collectors.toList()));

        RouteDivider divider = new RouteDivider(route,0.5);

        List<Shot> shots = divider.getShots();

        System.out.println();
        for(Shot s : shots){
            System.out.print(s);
        }

        currentStart = shots.get(0).getStart();

        for(Shot s : shots){
            Genetic.testCase = true;
            SimulateGenetic.initialize(20,currentStart,s.getEnd());
            Genetic.testCase = false;
            SimulateMain.simulator.get_course().set_holeTolerance(SimulateMain.simulator.get_course().get_hole_tolerance()+0.1);
            currentStart = SimulateGenetic.getLastEnd();
            System.out.println("currentstart"+currentStart.toString());
        }
    }

    public static void setStart(){
        double g = 9.81;
        double mass = 45;
        double mu = 0.131;
        double maxV = 60;
        double radius = 0.7;
        String height = "1";
        SimulateMain.beginning(g, mass, mu, maxV, radius, start, goal, height);
    }

    public static void addBotShots(Shot s){
        botShots.add(s);
    }
}
