package AI;

import main.Main;
import mazeAI.*;
import physics.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static mazeAI.Test.cToId;

public class TestAI {

    private static List<Shot> botShots = new ArrayList<Shot>();
    public static int shotCount = 0;

    public static List<Shot> getBotShots() {
        return botShots;
    }

    public static void takeAIShot(){

        SimulateMain.simulator.take_shot((Tools.velFromAngle(botShots.get(shotCount).getAngle(),botShots.get(shotCount).getSpeed())), false);
        Main.takingShot = true;
        shotCount++;

    }

    /**
     *
     * @param _start
     * @param _end
     */
    public static void runMazeAI(Vector2d _start, Vector2d _end){
        Main.aiRunning = true;
        GenerateNodes generator = new GenerateNodes(_start, _end,0.5,3);
        RouteFinder finder = new RouteFinder(generator.getMaze());
        List<CheckPoint> route = finder.findRoute(generator.getMaze().getNode(cToId(_start)),generator.getMaze().getNode(cToId(_end)));

        System.out.println();
        System.out.println(route.stream().map(CheckPoint::getId).collect(Collectors.toList()));

        RouteDivider divider = new RouteDivider(route,0.5);

        List<Shot> shots = divider.getShots();

        for(Shot s : shots){
            System.out.print(s);
        }
        System.out.println();
        System.out.println();

        Vector2d currentStart = shots.get(0).getStart();

        for(Shot s : shots){
            Genetic.testCase = true;
            SimulateGenetic.initialize(20, currentStart,s.getEnd());
            Genetic.testCase = false;
            SimulateMain.simulator.get_course().set_holeTolerance(SimulateMain.simulator.get_course().get_hole_tolerance()*1);
            currentStart = SimulateGenetic.getLastEnd();
        }

        SimulateMain.simulator.get_engine().resetPosition(_start);
        SimulateMain.simulator.set_ball_position(_start);
        System.out.println(_start);

        System.out.println("Done");

        System.out.println();

        takeAIShot();

        System.out.println(SimulateGenetic.getLastEnd());

        System.out.println(SimulateMain.getFlag().toString());
    }

    public static void addBotShots(Shot s){
        botShots.add(s);
    }
}
