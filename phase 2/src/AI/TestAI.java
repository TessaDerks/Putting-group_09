package AI;

import physics.*;

public class TestAI {

    public static void main(String []args){
        // initialize terrain

            if (setStart()) {
                int popSize = 25;
                Genetic.testCase = true;
                SimulateGenetic.initialize(popSize);
                Genetic.testCase = false;
            } else System.out.println("Ken niet");
    }

    public static boolean setStart(){
        double g = 9.81;
        double mass = 45;
        double mu = 0.131;
        double maxV = 50;
        double radius = 0.2;
        Vector2d start = new Vector2d(5.0, 5.0);
        Vector2d goal = new Vector2d(18.8,30.5);
        String height = "cos ( 0.5 x ) + 1";
        return SimulateMain.beginning(g, mass, mu, maxV, radius, start, goal, height);
    }
}
