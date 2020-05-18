package AI;

import main.Main;
import physics.*;

public class SimulateGenetic {
    private static Vector2d start = SimulateMain.getStart() ;



    // method to call from main to start AI with graphics
    public static void initialize(int popsize){
        //popSize = popsize;
        Genetic gen = new Genetic(popsize);
        gen.initializePopulation();
        gen.cocktailShaker();
        System.out.print(gen.getAngle());

        //xgen.calculateAngle();
        if (gen.CalculateAmountShots() > 1) {
            System.out.println(gen.CalculateAmountShots());
            System.out.println("Hole in one not possible, try different parameters");
            Main.openNewWindow = true;
            SimulateMain.simulator.get_engine().resetPosition(start);
        }
        else {
            //gen.calculateAngle();
            gen.takeFirstShot();
            gen.finishGame();
            System.out.println("Your bot scored after " + gen.getGeneration() + " generations!");
        }
    }
}
