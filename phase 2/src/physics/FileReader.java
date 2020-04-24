package physics;

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
    private static double goalX;
    private static double goalY;
    private static String height;


    public static Vector2d[] playShot(String fileName){
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
        Double angle = 0.0;
        Double speedd = 0.0;

        Vector2d[] velocity = new Vector2d[size];
        int velocityIndex = 0;

        for (int i = 0; i < size; i++){
            for (int line = 0; line < data.size(); line++) {
                String[] parts = data.get(line).split(" = ");

                String names = parts[0];
                String values = parts[1];


                if (names.equals("v") || names.equals("speed")) {
                    speedd = Double.valueOf(values);
                    //System.out.println(speedd);
                }
                if (names.equals("angle")) {
                    angle = Double.valueOf(values);
                    //System.out.println(angle);

                }
            }
            velocity[velocityIndex] = Tools.velFromAngle(angle,speedd);
            velocityIndex++;
            //System.out.println(this.velFromAngle(angle, speedd).getX() + " " + this.velFromAngle(angle, speedd).getY());
        }

            return velocity;
    }


    public void fileread(String path1) {

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
                System.out.println(startX);
            }
            if (list1.contains("startY")) {
                startY = Double.valueOf(values);
                System.out.println(startY);

            }


            if (list1.contains("goalX")) {    //needs adjusting
                goalX = Double.valueOf(values);
                System.out.println(goalX);

            }
            if (list1.contains("goalY")) {    //needs adjusting
                goalY = Double.valueOf(values);
                System.out.println(goalY);

            }

            if (list1.contains("height")) {      //needs adjusting
                //String masterFormula = ShuntingYard.postfix(values);
                //System.out.println(masterFormula);
                height = values;
                System.out.println(values);
            }

            //System.out.println(names);
            //System.out.println(values);
        }

    }
    //public static void main(String[] args) {

        //Vector2d[] x;
        //FileReader filereader = new FileReader();
        //x = filereader.playShot("C:\\Users\\teunh\\Documents\\FileShotTest.txt");
        //filereader.fileread("C:\\Users\\teunh\\Documents\\FileReaderTessts.txt");
        

    //}

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
