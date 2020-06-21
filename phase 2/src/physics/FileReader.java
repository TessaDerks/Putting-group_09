package physics;

import java.io.*;
import java.util.*;
public class FileReader {

    private static double g;
    private static double weight; 
    private static double mu;
    private static double speed;
    private static double radius;
    private static double startX;
    private static double startY;
    private static double goalX;
    private static double goalY;
    private static String height = "1";
    private static String heightMap = "";

    private static ArrayList<Vector2d> trees = new ArrayList<>();
    private static ArrayList<Vector2d> sand = new ArrayList<>();
    private static ArrayList<Tree> stumps = new ArrayList<>();

    private static Vector2d[] velocity;
    private static Vector2d start;
    private static Vector2d goal;

    // read file about shots, create array with shots

    /**
     * read file and make list of shots
     * @param fileName String, name of file that contains list with shots
     */
    public static void fileShot(String fileName){

        Scanner input;

        // add text in file to array by line
        ArrayList<String> data = new ArrayList<>();
        try {
            File file = new File(fileName);
            input = new Scanner(file);

            while (input.hasNextLine()) {
                data.add(input.nextLine());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // create array where shots will be added to
        int size = (data.size()/2);
        double angle = 0.0;
        double speedd = 0.0;

        velocity = new Vector2d[size];

        // fill array with shots from file
        for (int i = 0; i < size; i++){
            for (int line = i * 2; line < (2 + i * 2); line++) {

                String[] parts = data.get(line).split(" = ");
                String names = parts[0];
                String values = parts[1];

                if (names.equals("v") || names.equals("speed")) {
                    speedd = Double.parseDouble(values);
                }
                if (names.equals("angle")) {
                    angle = Double.parseDouble(values);
                }
            }
            velocity[i] = Tools.velFromAngle(angle,speedd);
        }
    }

    /**
     * get velocity of a shot at given index
     * @param index int, index of shot in list
     * @return Vector2d, velocity of shot that is stored at index in list
     */
    public static Vector2d getShot(int index){
        return velocity[index];
    }

    /**
     * get list with all shots
     * @return Vector2d[], list of shots
     */
    public static Vector2d[] getVelocity() {
        return velocity;
    }


    /**
     * read file to pass information about terrain
     * @param path1 String, name of file that gets read
     * @throws IOException
     */
    public void fileRead(String path1) throws IOException {

        Scanner input;

        ArrayList<String> data = new ArrayList<>();

        // add text in file to array by line
        try {
            File file = new File(path1);
            input = new Scanner(file);

            while (input.hasNextLine()) {
                data.add(input.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // go through list with data and split labels from values
        for (String datum : data) {
            String[] parts = datum.split(" = ");

            String names = parts[0];
            String values = parts[1];

            List<String> list1 = Collections.singletonList(names);

            if (list1.contains("g")) {
                g = Double.parseDouble(values);
            }

            if (list1.contains("m")) {
                weight = Double.parseDouble(values);
            }

            if (list1.contains("mu")) {
                mu = Double.parseDouble(values);
            }

            if (list1.contains("vmax")) {
                speed = Double.parseDouble(values);
            }

            if (list1.contains("tol")) {
                radius = Double.parseDouble(values);
            }

            if (list1.contains("startX")) {
                startX = Double.parseDouble(values);
            }
            if (list1.contains("startY")) {
                startY = Double.parseDouble(values);
            }

            start = new Vector2d(startX, startY);

            if (list1.contains("goalX")) {
                goalX = Double.parseDouble(values);

            }
            if (list1.contains("goalY")) {
                goalY = Double.parseDouble(values);
            }

            goal = new Vector2d(goalX, goalY);

            if (list1.contains("height")) {
                height = values;
            }

            if (list1.contains("treeLocation")) {
                String[] treeLoc = values.split(" ");

                for (int i = 0; i < treeLoc.length; i += 2) {
                    double treePositionX = Double.parseDouble(treeLoc[i]);
                    double treePositionY = Double.parseDouble(treeLoc[i + 1]);
                    Vector2d treePosition = new Vector2d(treePositionX, treePositionY);
                    trees.add(treePosition);
                }
            }

            if (list1.contains("sandLocation")) {
                String[] sandLoc = values.split(" ");
                for (int i = 0; i < sandLoc.length; i += 4) {
                    double sandTopX = Double.parseDouble(sandLoc[i]);
                    double sandTopY = Double.parseDouble(sandLoc[i + 1]);
                    double sandBtmX = Double.parseDouble(sandLoc[i + 2]);
                    double sandBtmY = Double.parseDouble(sandLoc[i + 3]);
                    Vector2d sandPosTop = new Vector2d(sandTopX, sandTopY);
                    Vector2d sandPosBtm = new Vector2d(sandBtmX, sandBtmY);
                    sand.add(sandPosTop);
                    sand.add(sandPosBtm);
                }
            }

            if (list1.contains("heightMap")) {
                heightMap = values;
            }

            if (list1.contains("stumpLocation")) {
                String[] stumpLoc = values.split(" ");

                for (int i = 0; i < stumpLoc.length; i += 2) {
                    double stumpPositionX = Double.parseDouble(stumpLoc[i]);
                    double stumpPositionY = Double.parseDouble(stumpLoc[i + 1]);
                    Vector2d stumpPosition = new Vector2d(stumpPositionX, stumpPositionY);
                    stumps.add(new Tree(stumpPosition, 0.5));
                }
            }
        }

        // send information to build terrain
        SimulateMain.beginning(g, weight, mu, speed, radius, start, goal, height,2, trees, sand, heightMap, stumps);
    }
}
