package view.fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class MainPageController {
    private static MainPageController instance;

    public static MainPageController getInstance() {
        if(instance == null) {
            instance = new MainPageController();
        }
        return instance;
    }

    private static final Stage Stage = new Stage();

    @FXML
    private TabPane tabPane;

    private final ImageView resistor = new ImageView(new Image("view/img/resistor-symbol.png"));
    private final ImageView capacitor = new ImageView(new Image("view/img/Symbol_Capacitor.png"));
    private final ImageView inductor = new ImageView(new Image("view/img/Inductor.png"));
    private final ImageView battery = new ImageView(new Image("view/img/battery.png"));

    public void addNewTab(ActionEvent actionEvent) {
        if (Main.hasFile) {
            Tab newTab = new NewTab("New Tab  ");
            tabPane.getTabs().add(newTab);
            tabPane.getSelectionModel().select(newTab);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No file exists! Please open a file first.");
            alert.show();
        }
    }

    public void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        File fileSelected = fileChooser.showOpenDialog(Stage);
        if (Main.runProgram(fileSelected)) {
            ArrayList<Tab> tabsForRemove = new ArrayList<>();
            for (Tab tab : tabPane.getTabs()) {
                if (tab.isClosable()) {
                    tabsForRemove.add(tab);
                }
            }
            for (Tab tab : tabsForRemove) {
                tabPane.getTabs().remove(tab);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("File is not readable!");
            alert.show();
        }
    }

    public void Exit(ActionEvent actionEvent) {
        Stage.close();
    }

    public static Stage getStage() {
        return Stage;
    }
}
