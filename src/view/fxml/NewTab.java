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
import javafx.scene.input.MouseEvent;
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
    private final Button draw;
    private final Button showGraph;
    private Type status;
    private AreaChart<Number, Number> chart;

    private final ArrayList<RadioButton> voltagesButtons;
    private final ArrayList<RadioButton> currentsButtons;
    private final ArrayList<RadioButton> powersButtons;

    public NewTab(String text) {
        super(text);
        this.chartPane = new GridPane();
        this.accordion = new Accordion();
        this.draw = new Button("Draw");
        this.showGraph = new Button("show graph");
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
        accordionVBox.setPadding(new Insets(5, 10, 5, 5));

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
        mainHBox.setPadding(new Insets(10, 10, 10, 10));
        //chartPane.setPadding(new Insets(10, 20, 10, 20));
        AreaChart<Number, Number> tempChart = new AreaChart<>(new NumberAxis(), new NumberAxis());
        tempChart.setPrefWidth(1920);
        tempChart.setPrefHeight(1080);
        chartPane.getChildren().add(tempChart);
        mainHBox.getChildren().addAll(accordionVBox, separator, chartPane);
        setStyle();
        this.setContent(mainHBox);
    }

    private void setStyle() {
        chartPane.setStyle("-fx-background-color: lightgray;" +
                "-fx-border-radius: 8, 7, 6;" +
                "-fx-background-insets: 0,1,2;");

        draw.setStyle("-fx-background-color: whitesmoke;" +
                "    -fx-background-radius: 8,7,6;" +
                "    -fx-background-insets: 0,1,2;" +
                "    -fx-text-fill: black;" +
                "-fx-font-style: italic;" +
                "-fx-font-size: 14px");
        draw.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                draw.setStyle("-fx-background-color: whitesmoke;" +
                        "    -fx-background-radius: 8,7,6;" +
                        "    -fx-background-insets: 0,1,2;" +
                        "    -fx-text-fill: steelblue;" +
                        "-fx-font-style: italic;" +
                        "    -fx-font-size: 14px;");
            }
        });
        draw.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                draw.setStyle("-fx-background-color: whitesmoke;" +
                        "    -fx-background-radius: 8,7,6;" +
                        "    -fx-background-insets: 0,1,2;" +
                        "    -fx-text-fill: black;" +
                        "-fx-font-style: italic;" +
                        "    -fx-font-size: 14px;");
            }
        });
        showGraph.setStyle("-fx-background-color: whitesmoke;" +
                "    -fx-background-radius: 8,7,6;" +
                "    -fx-background-insets: 0,1,2;" +
                "    -fx-text-fill: black;" +
                "-fx-font-style: italic;" +
                "    -fx-font-size: 14px;");
        showGraph.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showGraph.setStyle("-fx-background-color: whitesmoke;" +
                        "    -fx-background-radius: 8,7,6;" +
                        "    -fx-background-insets: 0,1,2;" +
                        "    -fx-text-fill: steelblue;" +
                        "-fx-font-style: italic;" +
                        "    -fx-font-size: 14px;");
            }
        });
        showGraph.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showGraph.setStyle("-fx-background-color: whitesmoke;" +
                        "    -fx-background-radius: 8,7,6;" +
                        "    -fx-background-insets: 0,1,2;" +
                        "    -fx-text-fill: black;" +
                        "-fx-font-style: italic;" +
                        "    -fx-font-size: 14px;");
            }
        });
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
        chart.setStyle("-fx-font-style: italic");
        chart.setCreateSymbols(false);
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
            Stage showGraphStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("showGraphPage.fxml"));
            Parent root = fxmlLoader.load();
            showGraphStage.setScene(new Scene(root));
            showGraphStage.setTitle("Show Graph");
            showGraphStage.getIcons().add(Main.stageIcon);
            ShowGraphPageController controller = fxmlLoader.getController();
            controller.initial(chart);
            showGraphStage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No chart! Please draw a chart first.");
            alert.show();
        }
    }
}