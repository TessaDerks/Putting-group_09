package sampl;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;


public class GUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * load main screen of GUI for the game
     * @param primaryStage Stage
     * @throws IOException if loader can't load screen
     */
    @Override
    public void start(@NotNull Stage primaryStage) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("GUIScreen.fxml"));

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);

        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();

    }
}
