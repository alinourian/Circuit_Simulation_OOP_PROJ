package view.fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.HashMap;

public class LoginPageController {
    private static LoginPageController instance;

    public static LoginPageController getInstance() {
        if(instance == null) {
            instance = new LoginPageController();
        }
        return instance;
    }

    @FXML
    private BorderPane borderPane;

    @FXML
    private Label loginLbl;

    @FXML
    private TextField userText;

    @FXML
    private PasswordField passText;

    private static final HashMap<String, String> userAndPass = new HashMap<>();

    public void login(ActionEvent actionEvent) throws Exception {
        String username = userText.getText();
        String password = passText.getText();
        addID();
        if (userAndPass.containsKey(username)) {
            if (userAndPass.get(username).equals(password)) {
                loginLbl.setText("login successful!");

                runMainPage();
                //drawGraph();
            }
        } else {
            loginLbl.setText("login failed!");
        }

    }

    private void runMainPage() throws Exception {
        Stage primaryStage = MainPageController.getStage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainPage.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle(Main.stageTitle);
        primaryStage.getIcons().add(Main.stageIcon);
        //primaryStage.setResizable(false);
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
        primaryStage.show();
    }

    private void addID() {
        userAndPass.put("98102527", "2150632063");
        userAndPass.put("user", "pass");
        userAndPass.put("", "");
    }

}
