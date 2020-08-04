package view.fxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    public static String stageTitle = "Spice";
    public static Image stageIcon = new Image("view\\img\\stage-Icon.png");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("loginPage.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle(Main.stageTitle);
        primaryStage.getIcons().add(stageIcon);
        primaryStage.show();
    }

    //TODO : diode - graphic - help page
}
