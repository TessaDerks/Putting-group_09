package AI;

import physics.*;

public class SimulateGenetic {
    private static final Vector2d start = SimulateMain.getStart() ;

    private static Vector2d lastEnd;


    // method to call from main to start AI with graphics

    /**
     *
     * @param popsize
     * @param start
     * @param end
     */
    public static void initialize(int popsize, Vector2d start, Vector2d end){
        Genetic gen = new Genetic(popsize,start,end);
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
