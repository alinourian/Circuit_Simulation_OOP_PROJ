package view.fxml;

import controller.InputController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Element;
import model.Node;
import model.Source;

import java.util.ArrayList;

public class Controller {

    private static final Stage Stage = new Stage();

    @FXML
    private TabPane tabPane;

    private final ImageView resistor = new ImageView(new Image("view/img/resistor-symbol.png"));
    private final ImageView capacitor = new ImageView(new Image("view/img/Symbol_Capacitor.png"));
    private final ImageView inductor = new ImageView(new Image("view/img/Inductor.png"));
    private final ImageView battery = new ImageView(new Image("view/img/battery.png"));

    public void addNewTab(ActionEvent actionEvent) {
        ArrayList<RadioButton> voltagesForDraw = new ArrayList<>();
        ArrayList<RadioButton> currentsForDraw = new ArrayList<>();
        ArrayList<RadioButton> powersForDraw = new ArrayList<>();

        AnchorPane anchorPane = new AnchorPane();
        Tab newTab = new Tab("New Tab  ");

        Accordion accordion = new Accordion();
        accordion.setMaxHeight(400);
        accordion.getPanes().add(addVoltagesChooser(voltagesForDraw));
        accordion.getPanes().add(addCurrentChooser(currentsForDraw));
        accordion.getPanes().add(addPowerChooser(powersForDraw));

        VBox vBox = new VBox(accordion);
        vBox.setLayoutX(700);

        anchorPane.getChildren().add(vBox);

        Button btn = new Button("btn");
        btn.setPrefWidth(50); btn.setPrefHeight(50);
        btn.setLayoutX(750); btn.setLayoutY(450);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                anchorPane.getChildren().add(compileData(voltagesForDraw));
                //compileData(currentsForDraw);
                //compileData(powersForDraw);
                //drawGraph(anchorPane);
            }
        });

        anchorPane.getChildren().add(btn);
        newTab.setContent(anchorPane);
        tabPane.getTabs().add(newTab);
    }

    public void drawGraph(AnchorPane anchorPane) {


    }

    private LineChart<Number, Number> compileData(ArrayList<RadioButton> graphsForDraw) {

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);

        for (RadioButton radioButton : graphsForDraw) {
            if (radioButton.isSelected()) {

                XYChart.Series<Number, Number> series = new XYChart.Series<>();
                series.setName(radioButton.getText());

                for (Node node : InputController.getInstance().getNodes()) {
                    if (radioButton.getText().equals("Node " + node.getName())) {
                        for (int i = 0; i < node.getVoltages().size(); i++) {
                            XYChart.Data<Number, Number> data;
                            data = new XYChart.Data<>(i * InputController.getInstance().getDeltaT(),
                                    node.getVoltages().get(i));
                            series.getData().add(data);
                        }
                        break;
                    }
                }
                chart.getData().add(series);
            }
        }

        return chart;
    }

    private TitledPane addVoltagesChooser(ArrayList<RadioButton> graphsForDraw) {
        VBox voltageBox = new VBox(15);
        voltageBox.setPadding(new Insets(15));

        ScrollPane scroll = new ScrollPane();
        scroll.setContent(voltageBox);

        for (Node node : InputController.getInstance().getNodes()) {
            RadioButton button = new RadioButton("Node " + node.getName());
            voltageBox.getChildren().add(button);
            graphsForDraw.add(button);
        }
        //ToggleGroup group1 = new ToggleGroup();
        //group1.getToggles().addAll(button1, button2, button3);
        //Adding the toggle button to the pane

        TitledPane pane = new TitledPane("Voltages", voltageBox);
        pane.setContent(scroll);
        pane.setPrefWidth(200);
        pane.setPrefHeight(400);
        pane.setLayoutX(1);
        pane.setLayoutY(1);

        return pane;
    }

    private TitledPane addCurrentChooser(ArrayList<RadioButton> graphsForDraw) {
        VBox currentBox = new VBox(15);
        currentBox.setPadding(new Insets(15));

        ScrollPane scroll = new ScrollPane();
        scroll.setContent(currentBox);

        for (Source source : InputController.getInstance().getSources()) {
            RadioButton button = new RadioButton(source.getName());
            currentBox.getChildren().add(button);
            graphsForDraw.add(button);
        }
        for (Element element : InputController.getInstance().getElements()) {
            RadioButton button = new RadioButton(element.getName());
            currentBox.getChildren().add(button);
            graphsForDraw.add(button);
        }
        //ToggleGroup group1 = new ToggleGroup();
        //group1.getToggles().addAll(button1, button2, button3);
        //Adding the toggle button to the pane

        TitledPane pane = new TitledPane("Currents", currentBox);
        pane.setContent(scroll);
        pane.setPrefWidth(200);
        pane.setPrefHeight(400);
        pane.setLayoutX(1);
        pane.setLayoutY(1);

        return pane;
    }

    private TitledPane addPowerChooser(ArrayList<RadioButton> graphsForDraw) {
        VBox powerBox = new VBox(15);
        powerBox.setPadding(new Insets(15));

        ScrollPane scroll = new ScrollPane();
        scroll.setContent(powerBox);

        for (Element element : InputController.getInstance().getElements()) {
            RadioButton button = new RadioButton(element.getName());
            powerBox.getChildren().add(button);
            graphsForDraw.add(button);
        }
        for (Source source : InputController.getInstance().getSources()) {
            RadioButton button = new RadioButton(source.getName());
            powerBox.getChildren().add(button);
            graphsForDraw.add(button);
        }
        //ToggleGroup group1 = new ToggleGroup();
        //group1.getToggles().addAll(button1, button2, button3);
        //Adding the toggle button to the pane

        TitledPane pane = new TitledPane("Powers", powerBox);
        pane.setContent(scroll);
        pane.setPrefWidth(200);
        pane.setPrefHeight(400);
        pane.setLayoutX(1);
        pane.setLayoutY(1);

        return pane;
    }

    public void Exit(ActionEvent actionEvent) {
        Stage.close();
    }

    public static Stage getStage() {
        return Stage;
    }
}
