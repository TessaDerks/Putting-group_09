package AI;

import main.Main;
import physics.*;

public class SimulateGenetic {
    private static Vector2d start = SimulateMain.getStart() ;

    private static Vector2d lastEnd;


    // method to call from main to start AI with graphics
    public static void initialize(int popsize, Vector2d start, Vector2d end){
        //popSize = popsize;
        Genetic gen = new Genetic(popsize,start,end);
        gen.initializePopulation();
        gen.cocktailShaker();

        //xgen.calculateAngle();
        /*
        if (gen.CalculateAmountShots() > 1) {
            System.out.println("Hole in one not possible, try different parameters");
            Main.openNewWindow = true;
            SimulateMain.simulator.get_engine().resetPosition(start);
        }
        else {
        */
            //gen.calculateAngle();
            gen.takeFirstShot();
            gen.finishGame();
            System.out.println("Your bot scored after " + gen.getGeneration() + " generations!");
            /*
        }

         */
        lastEnd = gen.getPopulation()[0].getPosition();
    }

    public static Vector2d getLastEnd() {
        return lastEnd;
    }
}
