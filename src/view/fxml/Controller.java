package view.fxml;

import controller.InputController;
import enums.Type;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Controller {
    private static Controller instance;

    public static Controller getInstance() {
        if(instance == null) {
            instance = new Controller();
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
        Tab newTab = new NewTab("New Tab  ");
        tabPane.getTabs().add(newTab);
    }

    public void Exit(ActionEvent actionEvent) {
        Stage.close();
    }

    public static Stage getStage() {
        return Stage;
    }
}
