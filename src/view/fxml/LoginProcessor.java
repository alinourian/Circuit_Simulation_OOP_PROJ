package view.fxml;

import controller.InputController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Node;

import java.util.HashMap;

public class LoginProcessor {

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

                runGraphDrawer();
                //drawGraph();
            }
        } else {
            loginLbl.setText("login failed!");
        }

    }

    private void drawGraph() {
        Stage primaryStage = new Stage();
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        /*for (Double point : Solver.result.keySet()) {
            XYChart.Data<Number, Number> data;
            data = new XYChart.Data<>(point, Solver.result.get(point));
            series.getData().add(data);
        }

         */
        Node node = InputController.getInstance().findNode("1");
        series.setName("node " + node.getName());
        for (int i = 0; i < node.getVoltages().size(); i++) {
            XYChart.Data<Number, Number> data;
            data = new XYChart.Data<>(i * InputController.getInstance().getDeltaT(),
                    node.getVoltages().get(i));
            series.getData().add(data);
        }

        //XYChart.Data<Number, Number> data1 = new XYChart.Data<>(0, -1);
        //XYChart.Data<Number, Number> data2 = new XYChart.Data<>(2, 1);
        //series.getData().addAll(data1, data2);

        chart.getData().add(series);
        primaryStage.setScene(new Scene(chart));
        //primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void runGraphDrawer() throws Exception {
        Stage primaryStage = Controller.getStage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainPage.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setScene(new Scene(root));
        //primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void addID() {
        userAndPass.put("98102527", "2150632063");
        userAndPass.put("user", "pass");
        userAndPass.put("", "");
    }
}
