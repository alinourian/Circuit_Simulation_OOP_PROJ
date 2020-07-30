package view.fxml;

import controller.InputController;
import enums.Type;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Element;
import model.Node;
import model.Source;

import java.io.IOException;
import java.util.ArrayList;

public class NewTab extends Tab {

    private final GridPane chartPane;
    private final Accordion accordion;
    private Type status;
    private AreaChart<Number, Number> chart;

    private final ArrayList<RadioButton> voltagesButtons;
    private final ArrayList<RadioButton> currentsButtons;
    private final ArrayList<RadioButton> powersButtons;

    public NewTab(String text) {
        super(text);
        this.chartPane = new GridPane();
        this.accordion = new Accordion();
        this.voltagesButtons = new ArrayList<>();
        this.currentsButtons = new ArrayList<>();
        this.powersButtons = new ArrayList<>();
        this.addNewTab();
    }

    private void addNewTab() {
        accordion.setMaxHeight(500);
        accordion.setMinWidth(200);
        TitledPane voltagePane = addChooser(voltagesButtons, Type.VOLTAGE);
        TitledPane currentPane = addChooser(currentsButtons, Type.CURRENT);
        TitledPane powerPane = addChooser(powersButtons, Type.POWER);

        accordion.getPanes().add(voltagePane);
        accordion.getPanes().add(currentPane);
        accordion.getPanes().add(powerPane);
        VBox accordionVBox = new VBox(5);
        accordionVBox.setPadding(new Insets(5, 5, 5, 5));

        Button draw = new Button("Draw");
        draw.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    chartPane.getChildren().clear();
                    if (status.equals(Type.VOLTAGE)) {
                        chartPane.add(getChart(voltagesButtons), 0, 0);
                    } else if (status.equals(Type.CURRENT)) {
                        chartPane.add(getChart(currentsButtons), 0, 0);
                    } else {
                        chartPane.add(getChart(powersButtons), 0, 0);
                    }
                } catch (NullPointerException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please choose something first.");
                    alert.show();
                }
            }
        });

        Button showGraph = new Button("show graph");
        showGraph.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    showGraph();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        HBox buttonHBox = new HBox();
        buttonHBox.setSpacing(5);
        buttonHBox.getChildren().addAll(draw, showGraph);
        accordionVBox.getChildren().addAll(buttonHBox, accordion);

        Separator separator = new Separator(Orientation.VERTICAL);
        HBox mainHBox = new HBox(5);
        //chartPane.setPadding(new Insets(10, 20, 10, 20));
        mainHBox.getChildren().addAll(accordionVBox, separator, chartPane);

        this.setContent(mainHBox);
    }

    private TitledPane addChooser(ArrayList<RadioButton> graphsForDraw, Type type) {
        VBox vBox = new VBox(15);
        vBox.setPadding(new Insets(15));

        ScrollPane scroll = new ScrollPane();
        scroll.setContent(vBox);

        TitledPane pane;
        if (type.equals(Type.VOLTAGE)) {
            for (Node node : InputController.getInstance().getNodes()) {
                RadioButton button = new RadioButton("Node " + node.getName());
                vBox.getChildren().add(button);
                graphsForDraw.add(button);
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        clearChecks(Type.VOLTAGE);
                    }
                });
            }
            pane = new TitledPane("Voltages", vBox);
        } else {
            for (Element element : InputController.getInstance().getElements()) {
                RadioButton button = new RadioButton(element.getName());
                vBox.getChildren().add(button);
                graphsForDraw.add(button);
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (type.equals(Type.CURRENT)) {
                            clearChecks(Type.CURRENT);
                        } else {
                            clearChecks(Type.POWER);
                        }
                    }
                });
            }
            for (Source source : InputController.getInstance().getSources()) {
                RadioButton button = new RadioButton(source.getName());
                vBox.getChildren().add(button);
                graphsForDraw.add(button);
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (type.equals(Type.CURRENT)) {
                            clearChecks(Type.CURRENT);
                        } else {
                            clearChecks(Type.POWER);
                        }
                    }
                });
            }
            if (type.equals(Type.CURRENT)) {
                pane = new TitledPane("Currents", vBox);
            } else {
                pane = new TitledPane("Powers", vBox);
            }
        }

        pane.setContent(scroll);
        pane.setPrefWidth(200);
        //pane.setPrefHeight(500);

        return pane;
    }

    private AreaChart<Number, Number> getChart(ArrayList<RadioButton> graphsForDraw) {

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        AreaChart<Number, Number> chart = new AreaChart<>(xAxis, yAxis);

        for (RadioButton radioButton : graphsForDraw) {
            if (radioButton.isSelected()) {
                if (status.equals(Type.VOLTAGE)) {
                    chart.getData().add(getVoltageChart(radioButton));
                    chart.setTitle("Voltage");
                } else if (status.equals(Type.CURRENT)) {
                    chart.getData().add(getCurrentChart(radioButton));
                    chart.setTitle("Current");
                } else {
                    chart.getData().add(getPowerChart(radioButton));
                    chart.setTitle("Power");
                }
            }
        }
        chart.setPrefWidth(1920);
        chart.setPrefHeight(1080);
        chart.setAlternativeColumnFillVisible(true);
        this.chart = chart;
        return chart;
    }

    private XYChart.Series<Number, Number> getVoltageChart(RadioButton radioButton) {
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
        return series;
    }

    private XYChart.Series<Number, Number> getCurrentChart(RadioButton radioButton) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(radioButton.getText());

        for (Element element : InputController.getInstance().getElements()) {
            if (radioButton.getText().equals(element.getName())) {
                for (int i = 0; i < element.getCurrents().size(); i++) {
                    XYChart.Data<Number, Number> data;
                    data = new XYChart.Data<>(i * InputController.getInstance().getDeltaT(),
                            element.getCurrents().get(i));
                    series.getData().add(data);
                }
                break;
            }
        }
        return series;
    }

    private XYChart.Series<Number, Number> getPowerChart(RadioButton radioButton) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(radioButton.getText());

        for (Element element : InputController.getInstance().getElements()) {
            if (radioButton.getText().equals(element.getName())) {
                for (int i = 0; i < element.getCurrents().size(); i++) {
                    XYChart.Data<Number, Number> data;
                    data = new XYChart.Data<>(i * InputController.getInstance().getDeltaT(),
                            element.getCurrents().get(i) * element.getVoltage());
                    series.getData().add(data);
                }
                break;
            }
        }
        return series;
    }

    private void clearChecks(Type type) {
        if (type.equals(Type.VOLTAGE)) {
            status = Type.VOLTAGE;
            for (RadioButton currentsButton : currentsButtons) {
                currentsButton.setSelected(false);
            }
            for (RadioButton powersButton : powersButtons) {
                powersButton.setSelected(false);
            }
        } else if (type.equals(Type.CURRENT)) {
            status = Type.CURRENT;
            for (RadioButton voltagesButton : voltagesButtons) {
                voltagesButton.setSelected(false);
            }
            for (RadioButton powersButton : powersButtons) {
                powersButton.setSelected(false);
            }
        } else {
            status = Type.POWER;
            for (RadioButton voltagesButton : voltagesButtons) {
                voltagesButton.setSelected(false);
            }
            for (RadioButton currentsButton : currentsButtons) {
                currentsButton.setSelected(false);
            }
        }
    }

    public void showGraph() throws IOException {
        if (chart != null) {
            Stage helpStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ShowGraphPage.fxml"));
            Parent root = fxmlLoader.load();
            helpStage.setScene(new Scene(root));
            helpStage.setTitle("Show Graph");
            helpStage.getIcons().add(Main.stageIcon);
            //helpStage.setResizable(false);
            ShowGraphPageController controller = fxmlLoader.getController();
            controller.initial(chart);
            helpStage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No chart! Please draw a chart first.");
            alert.show();
        }
    }

    public AreaChart<Number, Number> getChart() {
        return chart;
    }
}