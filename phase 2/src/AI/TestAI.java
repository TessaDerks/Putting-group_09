package AI;

import physics.*;

public class TestAI {

    public static void main(String []args){
        // initialize terrain
        if(setStart()){
            int popSize = 25;
            Genetic.testCase = true;
            SimulateGenetic.initialize(popSize);
            Genetic.testCase = false;
        }

    }

    public static boolean setStart(){
        double g = 9.81;
        double mass = 45;
        double mu = 0.131;
        double maxV = 30;
        double radius = 0.2;
        Vector2d start = new Vector2d(5.0, 5.0);
        Vector2d goal = new Vector2d(10.0,10.0);
        String height = "1";
        return SimulateMain.beginning(g, mass, mu, maxV, radius, start, goal, height);
    }
}
