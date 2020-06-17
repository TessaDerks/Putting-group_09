package AI;

import physics.*;

public class SimulateRA {
    static double g = 9.81;
    static double mass = 45;
    static double mu = 0.131;
    static double maxV = 80;
    static double radius = 0.2;
    static Vector2d start = new Vector2d(0.0, 5.0);
    static Vector2d end = new Vector2d(31.5,23.5);
    static String function = "cos ( 0.5 * x ) + 1";

    public static void main(String[] args) {

        SimulateMain.beginning(g, mass, mu, maxV, radius, start, end, function);

        RecursiveAlgorithm test = new RecursiveAlgorithm(start, end, new Function(function));

        if (!test.HoleInOnePossible()) {
            System.out.println(false);
            System.out.println("Hole in one not possible, try different parameters");
        }
        else {

        }
    }
}