package sampl;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import physics.FileReader;
import physics.SimulateMain;
import physics.Tree;
import physics.Vector2d;
import simplexNoise.ImageWriter;
import simplexNoise.SimplexNoise;
import MazeGenerator.MazeForSale;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {

    @FXML
    private ImageView perlin;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField gravity;

    @FXML
    private TextField mass;

    @FXML
    private TextField friction;

    @FXML
    private TextField startPosX;

    @FXML
    private TextField goalPosX;

    @FXML
    private TextField startPosY;

    @FXML
    private TextField goalPosY;

    @FXML
    private TextField treePosX;

    @FXML
    private TextField treePosY;

    @FXML
    private TextField sandTopX;

    @FXML
    private TextField sandTopY;

    @FXML
    private TextField sandBtmX;

    @FXML
    private TextField sandBtmY;

    @FXML
    private TextField radius;

    @FXML
    private TextField maxSpeed;

    @FXML
    private TextField heightProfile;

    @FXML
    private TextField courseCreator;

    @FXML
    private TextField listMoves;

    @FXML
    private TextField heightMapp;

    @FXML
    private TextField widthString;

    @FXML
    private TextField heightString;


    private ArrayList<Vector2d> treesList = new ArrayList<>();
    private ArrayList<Vector2d> sandList = new ArrayList<>();
    private ArrayList<Tree> stumps = new ArrayList<>();
    public double treeRadius;
    public static int line;
    private static final int MAZE_DIVIDED_BY_TWO = 10;

    /**
     * read textfields from GUI for terrain settings and send those to the game
     * @throws IOException if textfields are empty
     */
    @FXML
    private void applyMan() throws IOException {
        double gravityConstant = Double.parseDouble(gravity.getText());
        double massOfBall = Double.parseDouble(mass.getText());
        double frictionCoefficient = Double.parseDouble(friction.getText());
        double startX = Double.parseDouble(startPosX.getText());
        double startY = Double.parseDouble(startPosY.getText());
        Vector2d start = new Vector2d(startX, startY);
        double goalX = Double.parseDouble(goalPosX.getText());
        double goalY = Double.parseDouble(goalPosY.getText());
        Vector2d goal = new Vector2d(goalX, goalY);
        double radiusOfTarget = Double.parseDouble(radius.getText());
        double maxV = Double.parseDouble(maxSpeed.getText());
        String heightFunction = heightProfile.getText();
        String heightMap = heightMapp.getText();


        SimulateMain.beginning(gravityConstant, massOfBall, frictionCoefficient, maxV, radiusOfTarget, start, goal, heightFunction, 1, treesList, sandList, heightMap, stumps);
    }


    @FXML
    private void mazeGeneration() {

        treeRadius = 0.5;

        MazeForSale maze = new MazeForSale(MAZE_DIVIDED_BY_TWO,MAZE_DIVIDED_BY_TWO);
        line = 0;
        ArrayList<Tree> d = new ArrayList<>();

        // Create a stream to hold the output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);
        System.out.println(maze.toString());
        System.out.flush();
        System.setOut(old);
        Scanner scanner = new Scanner(baos.toString());
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            for (int i = 0; i < s.length(); i++){
                char c = s.charAt(i);

                if(c == '1'){
                    d.add(new Tree(new Vector2d(line,i), treeRadius));
                }
                else if (c == 'S'){
                    //  start (Btm Left in OpenGL)
                    double mazeStart = Double.parseDouble(String.valueOf(line));
                    String mazeStartString = Double.toString(mazeStart );
                    startPosX.setText(String.valueOf(line));
                    startPosY.setText(mazeStartString);

                }
                else if (c == 'E'){
                    double mazeEnd = Double.parseDouble(String.valueOf(line));
                    String mazeEndString = Double.toString(mazeEnd );
                    goalPosX.setText(String.valueOf(line));
                    goalPosY.setText(mazeEndString);
                }

            }
            line++;

        }
        scanner.close();

        stumps =  d;
    }

    /**
     * create random heightmap for terrain witht the use of perlin noise
     * @throws IOException if textfields are empty or filereader can't read file
     */
    @FXML
    private void randomNoiseGenerate() throws IOException {
        Random r = new Random();
        int largestFeature = r.nextInt(1000);
        float persistence = r.nextFloat()* 0.5f;
        int seedd = 0;
        System.out.println(largestFeature);
        System.out.println(persistence);
        System.out.println(seedd);

        int width = Integer.parseInt(widthString.getText());
        int height = Integer.parseInt(heightString.getText());

        SimplexNoise simplexNoise=new SimplexNoise(largestFeature,persistence, seedd);
        double xStart=0;
        double XEnd=500;
        double yStart=0;
        double yEnd=500;

        double[][] result=new double[width][height];

        for(int i = 0; i< width; i++){
            for(int j = 0; j< height; j++){
                int x=(int)(xStart+i*((XEnd-xStart)/ width));
                int y=(int)(yStart+j*((yEnd-yStart)/ height));
                result[i][j]=0.5*(1+simplexNoise.getNoise(x,y));
            }
        }

        ImageWriter.greyWriteImage(result);

        BufferedImage image1 = ImageIO.read(new File("res/saved.png"));
        Image image2 = SwingFXUtils.toFXImage(image1, null);
        perlin.setImage(image2);
    }


    /**
     * read textfields for file names to send to filereader to get terrain information
     * @throws IOException if textfields are empty
     */
    @FXML
    private void applyFile() throws IOException {
        String fileCourse = courseCreator.getText();
        String fileShots = listMoves.getText();
        FileReader fileReader = new FileReader();
        
        fileReader.fileRead(fileCourse);
        FileReader.fileShot(fileShots);
    }

    @FXML
    private void applyRandomTrees(){

        for(int i = 0; i< 15; i++){
            double x = Math.random() * (256 + 1);
            double y = Math.random() * (256 + 1);
            Vector2d tree = new Vector2d(x, y);
            treesList.add(tree);
        }
    }

    @FXML
    private void applyTreesButton(){
        double x = Double.parseDouble(treePosX.getText());
        double y = Double.parseDouble(treePosY.getText());
        treeRadius = 0.5;
        Vector2d tree = new Vector2d(x,y);
        treesList.add(tree);
    }

    @FXML
    private void resetTreeList(){
        treesList = new ArrayList<>();
    }


    @FXML
    private void applyRandomSand(){

        for(int i = 0; i< 15; i++){
            double xTop = Math.random() * (256 + 1);
            double yTop = Math.random() * (256 + 1);
            Vector2d sandTop = new Vector2d(xTop, yTop);
            sandList.add(sandTop);
            double xBtm = Math.random() * (256 + 1);
            double yBtm = Math.random() * (256 + 1);
            Vector2d sandBtm = new Vector2d(xBtm, yBtm);
            sandList.add(sandBtm);
        }
    }

    @FXML
    private void applySandButton(){
        double xTop = Double.parseDouble(sandTopX.getText());
        double yTop = Double.parseDouble(sandTopY.getText());
        Vector2d sandTop = new Vector2d(xTop,yTop);
        sandList.add(sandTop);
        double xBtm = Double.parseDouble(sandBtmX.getText());
        double yBtm = Double.parseDouble(sandBtmY.getText());
        Vector2d sandBtm =  new Vector2d(xBtm,yBtm);
        sandList.add(sandBtm);
    }

    @FXML
    private void resetSandList(){
        sandList = new ArrayList<>();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    @FXML
    private void start() {
        UILoader("Finish");
    }

    @FXML
    private void manInput(){
        UILoader("ManualInputScreen");
    }

    @FXML
    private void fileRead(){
        UILoader("FileReaderScreen");
    }

    @FXML
    private void exit(){
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }


    private void UILoader(String scene){
        Parent root = null;
        try{
            root = FXMLLoader.load(getClass().getResource(scene+".fxml"));
        } catch (IOException ex){
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        borderPane.setCenter(root);
    }
}
