package view.fxml;

import controller.InputController;
import controller.Solver;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.console.ConsoleScanner;
import view.file.FileScanner;
import view.file.SaveOnFile;

import java.io.File;
import java.io.IOException;

public class Main extends Application {
    public static boolean hasFile = false;

    public static void main(String[] args) {
        launch(args);
    }

    public static boolean runProgram(File file) {
        if (hasFile) {
            InputController.getInstance().restartProgram();
        }
        if (FileScanner.run(file)) {
            hasFile = true;
            //  Solve
            Solver solver = new Solver();
            solver.run();

            //  Write on file
            try {
                SaveOnFile.saveDataOnFile();
            } catch (IOException e) {
                System.err.println("Can not save on file");
            }

            //  Console scanning & Read file
            //ConsoleScanner.run();
            return true;
        }
        return false;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LoginPage.fxml"));
        Parent root = fxmlLoader.load();
        //primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
