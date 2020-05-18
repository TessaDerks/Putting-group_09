package AI;

import physics.*;

public class SimulateRA {
    private static Vector2d start = new Vector2d(0.0, 0.0);
    private static Vector2d end = new Vector2d(3.0, 2.0);
    private static Function2d function = new Function2d("1");

    public static void main(String[] args) {
        RecursiveAlgorithm test = new RecursiveAlgorithm(start, end, function);


        if (!test.HoleInOnePossible()) {
            System.out.println(false);
            System.out.println("Hole in one not possible, try different parameters");
        }
        else {

        }
    }
}