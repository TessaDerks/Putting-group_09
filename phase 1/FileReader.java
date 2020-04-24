import java.io.*;
import java.util.*;

class FileReader {

    private static double g;
    private static double weight; // grams
    private static double mu;
    private static double speed;
    private static double radius;
    private static double startX;
    private static double startY;
    private static Vector2d start = new Vector2d(startX, startY);
    private static double goalX;
    private static double goalY;
    private static Vector2d goal = new Vector2d(goalX, goalY);


    public static void fileread() {

        Scanner in = new Scanner(System.in);
        System.out.println("File path: ");

        String path = in.nextLine();

        Scanner input;

        ArrayList<String> data = new ArrayList<String>();

        try {
            File file = new File(path);
            input = new Scanner(file);

            while (input.hasNextLine()) {
                data.add(input.nextLine());
            }


        } catch (FileNotFoundException e) {
            System.out.println(e);
            return;
        }

        for (int line = 0; line < data.size(); line++) {
            String[] parts = data.get(line).split(" = ");

            String names = parts[0];
            String values = parts[1];

            List<String> list1 = Arrays.asList(names);

            if (list1.contains("g")) {
                g = Double.parseDouble(values);

                System.out.println(g);
            }

            if (list1.contains("m")) {
                weight = Double.parseDouble(values);

                System.out.println(weight);
            }

            if (list1.contains("mu")) {
                mu = Double.parseDouble(values);

                System.out.println(mu);
            }

            if (list1.contains("vmax")) {
                speed = Double.parseDouble(values);

                System.out.println(speed);
            }

            if (list1.contains("tol")) {
                radius = Double.parseDouble(values);

                System.out.println(radius);
            }

            if (list1.contains("startX")) {
                startX = Double.valueOf(values);
            }
            if (list1.contains("startY")) {
                startY = Double.valueOf(values);
            }


            if (list1.contains("goalX")) {    //needs adjusting
                goalX = Double.valueOf(values);
            }
            if (list1.contains("goalY")) {    //needs adjusting
                goalY = Double.valueOf(values);
            }

            if (list1.contains("height")) {      //needs adjusting

            }

            //System.out.println(names);
            //System.out.println(values);
        }

    }

    public static double getG() {
        return g;
    }

    public static void setG(double g) {
        FileReader.g = g;
    }

    public static double getWeight() {
        return weight;
    }

    public static void setWeight(double weight) {
        FileReader.weight = weight;
    }

    public static double getMu() {
        return mu;
    }

    public static void setMu(double mu) {
        FileReader.mu = mu;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public static double getRadius() {
        return radius;
    }

    public static void setRadius(double radius) {
        FileReader.radius = radius;
    }

    public static double getStartX() {
        return startX;
    }

    public static void setStartX(double startX) {
        FileReader.startX = startX;
    }

    public static double getStartY() {
        return startY;
    }

    public static void setStartY(double startY) {
        FileReader.startY = startY;
    }

    public static Vector2d getStart() {
        return start;
    }

    public static void setStart(Vector2d start) {
        FileReader.start = start;
    }

    public static double getGoalX() {
        return goalX;
    }

    public static void setGoalX(double goalX) {
        FileReader.goalX = goalX;
    }

    public static double getGoalY() {
        return goalY;
    }

    public static void setGoalY(double goalY) {
        FileReader.goalY = goalY;
    }

    public static Vector2d getGoal() {
        return goal;
    }

    public static void setGoal(Vector2d start) {
        FileReader.goal = goal;
    }
}