package physics;

import java.io.*;
import java.util.*;
public class FileReader {

    private static double g;
    private static double weight; // grams
    private static double mu;
    private static double speed;
    private static double radius;
    private static double startX;
    private static double startY;
    private static double goalX;
    private static double goalY;
    private static String height;

    public static Vector2d[] getVelocity() {
        return velocity;
    }

    private static Vector2d[] velocity;
    private static Vector2d start;
    private static Vector2d goal;


    public static void fileShot(String fileName){
        String path = fileName;

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
        }
        int size = (data.size()/2);
        double angle = 0.0;
        double speedd = 0.0;

        velocity = new Vector2d[size];
        int velocityIndex = 0;

        for (int i = 0; i < size; i++){

            for (int line = i * 2; line < (2 + i * 2); line++) {

                String[] parts = data.get(line).split(" = ");


                String names = parts[0];
                String values = parts[1];

                if (names.equals("v") || names.equals("speed")) {
                    speedd = Double.parseDouble(values);
                    System.out.println(speedd);
                }
                if (names.equals("angle")) {
                    angle = Double.parseDouble(values);
                    System.out.println(angle);
                }
                //velocity[i] = Tools.velFromAngle(angle,speedd);
            }
            velocity[i] = Tools.velFromAngle(angle,speedd);

            System.out.println(velocity[i].get_x() + " " + velocity[i].get_y());
            //System.out.println(this.velFromAngle(angle, speedd).getX() + " " + this.velFromAngle(angle, speedd).getY());
        }


    }

    public static Vector2d getShot(int index){
        return velocity[index];
    }


    public void fileRead(String path1) {

        String path = path1;

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

            List<String> list1 = Collections.singletonList(names);

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
                startX = Double.parseDouble(values);
                System.out.println(startX);
            }
            if (list1.contains("startY")) {
                startY = Double.parseDouble(values);
                System.out.println(startY);
            }

            start = new Vector2d(startX, startY);

            if (list1.contains("goalX")) {    //needs adjusting
                goalX = Double.parseDouble(values);
                System.out.println(goalX);

            }
            if (list1.contains("goalY")) {    //needs adjusting
                goalY = Double.parseDouble(values);
                System.out.println(goalY);
            }

            goal = new Vector2d(goalX, goalY);

            if (list1.contains("height")) {      //needs adjusting
                //String masterFormula = ShuntingYard.postfix(values);
                //System.out.println(masterFormula);
                height = values;
                System.out.println(values);
            }
        }
        SimulateMain.beginning(g, weight, mu, speed, radius, start, goal, height,2);
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
        FileReader.speed = speed;
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
    public static String getHeight(){
        return height;
    }
    
}
