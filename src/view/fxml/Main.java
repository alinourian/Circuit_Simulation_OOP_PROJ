package view.fxml;

import controller.UnionCreator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.fileScanner.FileScanner;

public class Main extends Application {
    public static void main(String[] args) {
        if (FileScanner.run()) {

            UnionCreator unionCreator = new UnionCreator();

            unionCreator.run();
            //if( unionCreator.run() )
            //{
            //Solver solver = new Solver();
            //solver.run();
            //}


            //ShowCircuit.showInConsole();
            //write on file
            //run console
            launch(args);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println(Main.class.getResource("Graph.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Graph.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
