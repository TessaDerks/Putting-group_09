package sampl;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;
import physics.FileReader;
import physics.SimulateMain;
import physics.Vector2d;
import terrain.Terrain;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {

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
    private Button applyFileReader;

    @FXML
    private Button applyManInput;

    private ArrayList<Vector2d> treesList = new ArrayList<>();

    private ArrayList<Vector2d> sandList = new ArrayList<>();

    // read textfield from manual input screen and send information about terrain to simulate main

    @FXML
    private void applyMan(MouseEvent event) throws IOException {
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

        SimulateMain.beginning(gravityConstant, massOfBall, frictionCoefficient, maxV, radiusOfTarget, start, goal, heightFunction, 1, treesList, sandList);
    }


  /*  private String translateFunction(double startX, double startY, String function){
        for(int i = 0; i<function.length();i++){

            String findX = Character.toString(function.charAt(i));
            if (findX.equals("x")){
                String subX = function.substring(0, i-1);
                String a = Double.toString(startX);
                subX = subX + " ( x - " + a + " )";
                function = subX + function.substring(i+1, function.length());
                System.out.println(function);
                i+=2;
            }
            if (findX.equals("y")){
                String subX = function.substring(0, i-1);
                String a = Double.toString(startY);
                subX = subX + " ( y - " + a + " )";
                function = subX + function.substring(i+1, function.length());
                System.out.println(function);
                i+=2;
            }
        }
        return function;
    }

   */

    // read textfields from file input screen and send information to filereader 
    @FXML
    private void applyFile(MouseEvent event) throws IOException {
        String fileCourse = courseCreator.getText();
        String fileShots = listMoves.getText();
        FileReader fileReader = new FileReader();
        
        fileReader.fileRead(fileCourse);
        FileReader.fileShot(fileShots);
    }

    @FXML
    private void applyRandomTrees(MouseEvent event){

        for(int i = 0; i< 15; i++){
            double x = Math.random() * (800 + 1);
            double y = Math.random() * (800 + 1);
            Vector2d tree = new Vector2d(x, y);
            treesList.add(tree);
        }
    }

    @FXML
    private void resetTreeList(MouseEvent event){
        treesList = new ArrayList<>();
    }

    @FXML
    private void resetSandList(MouseEvent event){
        sandList = new ArrayList<>();
    }


    @FXML
    private void applyRandomSand(MouseEvent event){

        for(int i = 0; i< 15; i++){
            double xTop = Math.random() * (800 + 1);
            double yTop = Math.random() * (800 + 1);
            Vector2d sandTop = new Vector2d(xTop, yTop);
            sandList.add(sandTop);
            double xBtm = Math.random() * (800 + 1);
            double yBtm = Math.random() * (800 + 1);
            Vector2d sandBtm = new Vector2d(xBtm, yBtm);
            sandList.add(sandBtm);
        }
    }

    @FXML
    private void applyTreesButton(MouseEvent event){
        double x = Double.parseDouble(treePosX.getText());
        double y = Double.parseDouble(treePosY.getText());
        Vector2d tree = new Vector2d(x,y);
        treesList.add(tree);
    }

    @FXML
    private void applySandButton(MouseEvent event){
        double xTop = Double.parseDouble(sandTopX.getText());
        double yTop = Double.parseDouble(sandTopY.getText());
        Vector2d sandTop = new Vector2d(xTop,yTop);
        sandList.add(sandTop);
        double xBtm = Double.parseDouble(sandBtmX.getText());
        double yBtm = Double.parseDouble(sandBtmY.getText());
        Vector2d sandBtm =  new Vector2d(xBtm,yBtm);
        sandList.add(sandBtm);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void start(MouseEvent event) {
        UILoader("Finish");
    }

    @FXML
    private void manInput(MouseEvent event){
        UILoader("ManualInputScreen");
    }

    @FXML
    private void fileRead(MouseEvent event){
        UILoader("FileReaderScreen");
    }

    @FXML
    private void exit(MouseEvent event){
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
