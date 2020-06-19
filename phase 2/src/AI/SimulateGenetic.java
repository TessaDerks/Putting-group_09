package AI;

import physics.*;

public class SimulateGenetic {

    private static Vector2d lastEnd;


    // method to call from main to start AI with graphics

    /**
     *
     * @param popSize int, popSize > 0
     * @param start Vector2d
     * @param end Vector2d
     */
    public static void initialize(int popSize, Vector2d start, Vector2d end){
        Genetic gen = new Genetic(popSize,start,end);
        gen.initializePopulation();
        gen.cocktailShaker();

            gen.takeFirstShot();
            gen.finishGame();
            System.out.println("Your bot scored after " + gen.getGeneration() + " generations!");

        lastEnd = gen.getPopulation()[0].getPosition();
    }

    public static Vector2d getLastEnd() {
        return lastEnd;
    }
}
