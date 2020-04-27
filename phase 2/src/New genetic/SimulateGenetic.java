public class SimulateGenetic {
    private static Vector2d start = new Vector2d(0.0, 0.0);
    private static Vector2d end = new Vector2d(3.0 , 2.0);
    private static int popSize = 25;
    private static Function2d function = new Function2d("1");

    public static void main(String[] args) {
        Genetic gen = new Genetic(start, end, popSize, function);
        gen.initializePopulation();
        gen.cocktailShaker();
        System.out.print(gen.getAngle());



        //xgen.calculateAngle();
        if (gen.CalculateAmountShots() > 1) {
            System.out.println(gen.CalculateAmountShots());
            System.out.println("Hole in one not possible, try different parameters");
        }
        else {
            //gen.calculateAngle();
            gen.takeFirstShot();
            gen.finishGame();
            System.out.println("Your bot scored after " + gen.getGeneration() + " generations!");
        }
    }
}
