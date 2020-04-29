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

import java.io.IOException;
import java.net.URL;
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

@FXML
    private void applyMan(MouseEvent event){
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

        SimulateMain.beginning(gravityConstant, massOfBall, frictionCoefficient, maxV, radiusOfTarget, start, goal, heightFunction, 1);
    }

    @FXML
    private void applyFile(MouseEvent event){
        String fileCourse = courseCreator.getText();
        String fileShots = listMoves.getText();
        FileReader fileReader = new FileReader();
        System.out.println(fileCourse);
        fileReader.fileRead(fileCourse);
        System.out.println(fileShots);
        FileReader.fileShot(fileShots);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void start(MouseEvent event){
        new Main().start();
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
