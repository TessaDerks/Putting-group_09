package AI;

import physics.*;

public class TestAI {

    private static Vector2d start = new Vector2d(50.0, -50.0);
    private static Vector2d goal = new Vector2d(31.5,21.5);

    public static void main(String []args){
        // initialize terrain
        setStart();
        /*
        int popSize = 25;
        Genetic.testCase = true;
        Tools tools = new Tools();
        //tools.adjustFlagPosition(start, goal);
        SimulateGenetic.initialize(popSize);
        Genetic.testCase = false;
         */
    }

    public static void setStart(){
        double g = 9.81;
        double mass = 45;
        double mu = 0.131;
        double maxV = 80;
        double radius = 0.2;
        String height = "1";
        SimulateMain.beginning(g, mass, mu, maxV, radius, start, goal, height);
    }
}
