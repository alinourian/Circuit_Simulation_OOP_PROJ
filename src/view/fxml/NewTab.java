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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Element;
import model.Node;
import model.Source;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class NewTab extends Tab {

    private final GridPane chartPane;
    private final Accordion accordion;
    private final Button draw;
    private final Button showGraph;
    private final Button advanceDraw;
    private Type status;
    private AreaChart<Number, Number> chart;

    private final ArrayList<RadioButton> voltagesButtons;
    private final ArrayList<RadioButton> currentsButtons;
    private final ArrayList<RadioButton> powersButtons;
    private final ArrayList<String> advanceDrawActions;

    public NewTab(String text) {
        super(text);
        this.chartPane = new GridPane();
        this.accordion = new Accordion();
        this.draw = new Button("Draw");
        this.showGraph = new Button("show graph");
        this.advanceDraw = new Button("Advance Draw");
        this.voltagesButtons = new ArrayList<>();
        this.currentsButtons = new ArrayList<>();
        this.powersButtons = new ArrayList<>();
        advanceDrawActions = new ArrayList<>();
        this.initial();
    }

    private void initial() {
        accordion.setMaxHeight(500);
        accordion.setMinWidth(200);
        TitledPane voltagePane = addChooserBox(voltagesButtons, Type.VOLTAGE);
        TitledPane currentPane = addChooserBox(currentsButtons, Type.CURRENT);
        TitledPane powerPane = addChooserBox(powersButtons, Type.POWER);

        accordion.getPanes().add(voltagePane);
        accordion.getPanes().add(currentPane);
        accordion.getPanes().add(powerPane);
        VBox accordionVBox = new VBox(10);
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

        advanceDraw.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                advanceChartDraw();
            }
        });

        HBox buttonHBox = new HBox();
        buttonHBox.setSpacing(5);
        buttonHBox.getChildren().addAll(draw, showGraph);
        accordionVBox.getChildren().addAll(buttonHBox, advanceDraw, accordion);

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
        advanceDraw.setStyle("-fx-background-color: whitesmoke;" +
                "    -fx-background-radius: 8,7,6;" +
                "    -fx-background-insets: 0,1,2;" +
                "    -fx-text-fill: black;" +
                "-fx-font-style: italic;" +
                "-fx-font-size: 14px");
        advanceDraw.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                advanceDraw.setStyle("-fx-background-color: whitesmoke;" +
                        "    -fx-background-radius: 8,7,6;" +
                        "    -fx-background-insets: 0,1,2;" +
                        "    -fx-text-fill: steelblue;" +
                        "-fx-font-style: italic;" +
                        "    -fx-font-size: 14px;");
            }
        });
        advanceDraw.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                advanceDraw.setStyle("-fx-background-color: whitesmoke;" +
                        "    -fx-background-radius: 8,7,6;" +
                        "    -fx-background-insets: 0,1,2;" +
                        "    -fx-text-fill: black;" +
                        "-fx-font-style: italic;" +
                        "    -fx-font-size: 14px;");
            }
        });
    }

    private TitledPane addChooserBox(ArrayList<RadioButton> graphsForDraw, Type type) {
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
                            -1 * element.getCurrents().get(i));
                    series.getData().add(data);
                }
                break;
            }
        }
        for (Source source : InputController.getInstance().getSources()) {
            if (radioButton.getText().equals(source.getName())) {
                for (int i = 0; i < source.getCurrents().size(); i++) {
                    XYChart.Data<Number, Number> data;
                    data = new XYChart.Data<>(i * InputController.getInstance().getDeltaT(),
                            source.getCurrents().get(i));
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
                            element.getCurrents().get(i) *
                                    (element.getNodeP().getVoltages().get(i) - element.getNodeN().getVoltages().get(i)));
                    series.getData().add(data);
                }
                break;
            }
        }
        for (Source source : InputController.getInstance().getSources()) {
            if (radioButton.getText().equals(source.getName())) {
                for (int i = 0; i < source.getCurrents().size(); i++) {
                    XYChart.Data<Number, Number> data;
                    data = new XYChart.Data<>(i * InputController.getInstance().getDeltaT(),
                            source.getCurrents().get(i) *
                                    (source.getNodeP().getVoltages().get(i) - source.getNodeN().getVoltages().get(i)));
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

    // ***********************

    public void advanceChartDraw() {
        // Create the custom dialog.
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Advance Drawer");
        dialog.setHeaderText("Enter what you want to draw...");
        Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
        dialogStage.getIcons().add(new Image("view/img/sinusoid-pngrepo-com.png"));
        //dialog.initOwner(MainPageController.getStage());
        ImageView imageView = new ImageView(new Image("view/img/sinusoid-pngrepo-com.png"));
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        dialog.setGraphic(imageView);
        // Set the button types.
        ButtonType drawButtonType = new ButtonType("Draw", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(drawButtonType, ButtonType.CANCEL);


        // CREATE MOTHER BOX
        VBox vBox = new VBox(10);
        //vBox.setPrefWidth(200);
        vBox.setPrefHeight(400);

        // FIRST CHILD
        TextField textField = new TextField();
        textField.setPromptText("Enter your equation ...");
        textField.setEditable(false);
        textField.setMinWidth(500);
        textField.setMinHeight(40);
        textField.setStyle("-fx-font-size: 14px");

        // SECOND CHILD
        HBox hBox = new HBox();
        hBox.setSpacing(20);

        VBox elementsVBox = new VBox();

        ScrollPane elementsScroll = new ScrollPane();
        elementsScroll.setContent(elementsVBox);

        double xButton = 100, yButton = 30;
        for (Node node : InputController.getInstance().getNodes()) {
            Button button = new Button("V[" + node.getName() + "]");
            setButtonStyle(button, textField);
            button.setPrefSize(xButton, yButton);
            elementsVBox.getChildren().add(button);
        }
        for (Element element : InputController.getInstance().getElements()) {
            Button button1 = new Button("I[" + element.getName() + "]");
            setButtonStyle(button1, textField);
            button1.setPrefSize(xButton, yButton);
            elementsVBox.getChildren().add(button1);
            Button button2 = new Button("P[" + element.getName() + "]");
            setButtonStyle(button2, textField);
            button2.setPrefSize(xButton, yButton);
            elementsVBox.getChildren().add(button2);
        }
        for (Source source : InputController.getInstance().getSources()) {
            Button button1 = new Button("I[" + source.getName() + "]");
            setButtonStyle(button1, textField);
            button1.setPrefSize(xButton, yButton);
            elementsVBox.getChildren().add(button1);
            Button button2 = new Button("P[" + source.getName() + "]");
            setButtonStyle(button2, textField);
            button2.setPrefSize(xButton, yButton);
            elementsVBox.getChildren().add(button2);
        }

        Accordion buttonAccordion = new Accordion();
        //buttonAccordion.setMinHeight(200);
        buttonAccordion.setMinWidth(200);
        TitledPane buttonPane = new TitledPane();
        buttonPane.setText("Tools");
        buttonPane.setContent(elementsScroll);
        buttonAccordion.getPanes().add(buttonPane);
        Separator separator = new Separator(Orientation.VERTICAL);
        ScrollPane calcScroll = new ScrollPane();
        calcScroll.setContent(getCalculationPane(textField));
        hBox.getChildren().addAll(buttonAccordion, separator,calcScroll);
        vBox.getChildren().addAll(textField, hBox);
        dialog.getDialogPane().setContent(vBox);

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == drawButtonType) {
                return textField.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(s -> {
            //calculateInput(s);
            System.out.println("text = " + s);
        });
    }

    public void calculateInput(String string) {
        String firstAction = advanceDrawActions.get(0);
        ArrayList<Double> action0 = getArrayListFromName(firstAction);
        String secondAction = advanceDrawActions.get(1);
        for (int i = 2; i < advanceDrawActions.size() - 2; i+=2) {
            String action = advanceDrawActions.get(i);
            action0 = calc(action0, getArrayListFromName(action), secondAction);
            secondAction = advanceDrawActions.get(i + 1);
        }
        assert action0 != null;
        drawCustomChart(action0, "Custom", "");
    }

    private ArrayList<Double> getArrayListFromName(String string) {
        if (string.startsWith("V")) {
            for (Node node : InputController.getInstance().getNodes()) {
                if (string.matches("I[" + node.getName() + "]")) {
                    return node.getVoltages();
                }
            }
        } else if (string.startsWith("I")) {
            for (Element element : InputController.getInstance().getElements()) {
                if (string.matches("I[" + element.getName() + "]")) {
                    return element.getCurrents();
                }
            }
            for (Source source : InputController.getInstance().getSources()) {
                if (string.matches("I[" + source.getName() + "]")) {
                    return source.getCurrents();
                }
            }
        } else if (string.startsWith("P")) {
            for (Element element : InputController.getInstance().getElements()) {
                if (string.matches("P[" + element.getName() + "]")) {
                    return multiplyCharts(element.getCurrents(),
                            subCharts(element.getNodeP().getVoltages(), element.getNodeN().getVoltages()));
                }
            }
            for (Source source : InputController.getInstance().getSources()) {
                if (string.matches("P[" + source.getName() + "]")) {
                    return multiplyCharts(source.getCurrents(),
                            subCharts(source.getNodeP().getVoltages(), source.getNodeN().getVoltages()));

                }
            }
        }
        return null;
    }

    private ArrayList<Double> calc(ArrayList<Double> arrayList1, ArrayList<Double> arrayList2, String statue) {
        switch (statue) {
            case " + ":
                return sumCharts(arrayList1, arrayList2);
            case " - ":
                return subCharts(arrayList1, arrayList2);
            case " * ":
                return multiplyCharts(arrayList1, arrayList2);
            default:
                return divideCharts(arrayList1, arrayList2);
        }
    }

    public void drawCustomChart(ArrayList<Double> chartList, String name, String title) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(name);

        for (int i = 0; i < chartList.size(); i++) {
            XYChart.Data<Number, Number> data;
            data = new XYChart.Data<>(i * InputController.getInstance().getDeltaT(), chartList.get(i));
            series.getData().add(data);
        }
        chartPane.getChildren().clear();
        AreaChart<Number, Number> tempChart = new AreaChart<>(new NumberAxis(), new NumberAxis());
        tempChart.getData().add(series);
        tempChart.setTitle(title);
        tempChart.setStyle("-fx-font-style: italic");
        tempChart.setCreateSymbols(false);
        tempChart.setPrefWidth(1920);
        tempChart.setPrefHeight(1080);
        chartPane.getChildren().add(tempChart);
    }

    public static ArrayList<Double> sumCharts(ArrayList<Double> chart1, ArrayList<Double> chart2) {
        ArrayList<Double> result = new ArrayList<>();
        for (int i = 0; i < Math.min(chart1.size(), chart2.size()); i++) {
            result.add(chart1.get(i) + chart2.get(i));
        }
        return result;
    }

    public static ArrayList<Double> subCharts(ArrayList<Double> chart1, ArrayList<Double> chart2) {
        ArrayList<Double> result = new ArrayList<>();
        for (int i = 0; i < Math.min(chart1.size(), chart2.size()); i++) {
            result.add(chart1.get(i) - chart2.get(i));
        }
        return result;
    }

    public static ArrayList<Double> multiplyCharts(ArrayList<Double> chart1, ArrayList<Double> chart2) {
        ArrayList<Double> result = new ArrayList<>();
        for (int i = 0; i < Math.min(chart1.size(), chart2.size()); i++) {
            result.add(chart1.get(i) * chart2.get(i));
        }
        return result;
    }

    public static ArrayList<Double> divideCharts(ArrayList<Double> chart1, ArrayList<Double> chart2) {
        ArrayList<Double> result = new ArrayList<>();
        for (int i = 0; i < Math.min(chart1.size(), chart2.size()); i++) {
            result.add(chart1.get(i) / chart2.get(i));
        }
        return result;
    }

    //Draw advanceDraw dialog buttons
    private Pane getCalculationPane(TextField textField) {
        Pane calculationPane = new Pane();
        calculationPane.setPrefWidth(245);
        calculationPane.setPrefHeight(328);
        addNumbers(calculationPane, textField);
        addCalculationButtons(calculationPane, textField);
        addControllerButton(calculationPane, textField);
        return calculationPane;
    }

    private void addControllerButton(Pane calculationPane, TextField textField) {
        Button clear = new Button("Clear");
        Button back = new Button("Back");
        clear.setLayoutX(0); back.setLayoutX(122);
        clear.setLayoutY(0); back.setLayoutY(0);
        clear.setPrefWidth(122); back.setPrefWidth(122);
        clear.setPrefHeight(60); back.setPrefHeight(60);
        setControllerButtonStyle(clear, back, textField);
        calculationPane.getChildren().addAll(clear, back);
    }

    private void addNumbers(Pane calculationPane, TextField textField) {
        for (int i = 1; i < 10; i++) {
            Button button = new Button(Integer.toString(i));
            button.setPrefWidth(61);
            button.setPrefHeight(67);
            switch (i) {
                case 1:
                    button.setLayoutX(0);
                    button.setLayoutY(194);
                    break;
                case 2:
                    button.setLayoutX(61);
                    button.setLayoutY(194);
                    break;
                case 3:
                    button.setLayoutX(122);
                    button.setLayoutY(194);
                    break;
                case 4:
                    button.setLayoutX(0);
                    button.setLayoutY(127);
                    break;
                case 5:
                    button.setLayoutX(61);
                    button.setLayoutY(127);
                    break;
                case 6:
                    button.setLayoutX(122);
                    button.setLayoutY(127);
                    break;
                case 7:
                    button.setLayoutX(0);
                    button.setLayoutY(60);
                    break;
                case 8:
                    button.setLayoutX(61);
                    button.setLayoutY(60);
                    break;
                default:
                    button.setLayoutX(122);
                    button.setLayoutY(60);
                    break;
            }
            setButtonStyle(button, textField);
            calculationPane.getChildren().add(button);
        }
        Button button0 = new Button("0");
        Button buttonDote = new Button(".");
        button0.setLayoutX(0); buttonDote.setLayoutX(122);
        button0.setLayoutY(261); buttonDote.setLayoutY(261);
        button0.setPrefWidth(122); buttonDote.setPrefWidth(61);
        button0.setPrefHeight(67); buttonDote.setPrefHeight(67);
        setButtonStyle(button0, textField); setButtonStyle(buttonDote, textField);
        calculationPane.getChildren().addAll(button0, buttonDote);
    }

    private void addCalculationButtons(Pane calculationPane, TextField textField) {
        Button add = new Button(" + ");
        Button sub = new Button(" - ");
        Button multiply = new Button(" * ");
        Button divide = new Button(" / ");
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(add);
        buttons.add(sub);
        buttons.add(multiply);
        buttons.add(divide);
        for (Button button : buttons) {
            setButtonStyle(button, textField);
            button.setLayoutX(182);
            button.setPrefSize(61, 67);
        }
        add.setLayoutY(261);
        sub.setLayoutY(194);
        multiply.setLayoutY(127);
        divide.setLayoutY(60);
        calculationPane.getChildren().addAll(add, sub, multiply, divide);
    }

    private void setButtonStyle(Button button, TextField textField) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                advanceDrawActions.add(button.getText());
                textField.setText(textField.getText() + button.getText());
            }
        });
        button.setStyle("    -fx-background-radius: 8,7,6;" +
                "    -fx-background-insets: 0,1,2;" +
                "    -fx-text-fill: black;" +
                "-fx-font-style: italic;" +
                "    -fx-font-size: 12px;");
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.setStyle("    -fx-background-radius: 8,7,6;" +
                        "    -fx-background-insets: 0,1,2;" +
                        "    -fx-text-fill: steelblue;" +
                        "-fx-font-style: italic;" +
                        "    -fx-font-size: 12px;");
            }
        });
        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.setStyle("    -fx-background-radius: 8,7,6;" +
                        "    -fx-background-insets: 0,1,2;" +
                        "    -fx-text-fill: black;" +
                        "-fx-font-style: italic;" +
                        "    -fx-font-size: 12px;");
            }
        });
    }

    private void setControllerButtonStyle(Button clear, Button back, TextField textField) {
        clear.setStyle("    -fx-background-radius: 8,7,6;" +
                "    -fx-background-insets: 0,1,2;" +
                "    -fx-text-fill: black;" +
                "-fx-font-style: italic;" +
                "    -fx-font-size: 14px;");
        clear.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clear.setStyle("    -fx-background-radius: 8,7,6;" +
                        "    -fx-background-insets: 0,1,2;" +
                        "    -fx-text-fill: steelblue;" +
                        "-fx-font-style: italic;" +
                        "    -fx-font-size: 12px;");
            }
        });
        clear.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clear.setStyle("    -fx-background-radius: 8,7,6;" +
                        "    -fx-background-insets: 0,1,2;" +
                        "    -fx-text-fill: black;" +
                        "-fx-font-style: italic;" +
                        "    -fx-font-size: 12px;");
            }
        });
        back.setStyle("    -fx-background-radius: 8,7,6;" +
                "    -fx-background-insets: 0,1,2;" +
                "    -fx-text-fill: black;" +
                "-fx-font-style: italic;" +
                "    -fx-font-size: 14px;");
        back.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                back.setStyle("    -fx-background-radius: 8,7,6;" +
                        "    -fx-background-insets: 0,1,2;" +
                        "    -fx-text-fill: steelblue;" +
                        "-fx-font-style: italic;" +
                        "    -fx-font-size: 12px;");
            }
        });
        back.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                back.setStyle("    -fx-background-radius: 8,7,6;" +
                        "    -fx-background-insets: 0,1,2;" +
                        "    -fx-text-fill: black;" +
                        "-fx-font-style: italic;" +
                        "    -fx-font-size: 12px;");
            }
        });

        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textField.setText("");
                advanceDrawActions.clear();
            }
        });
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (advanceDrawActions.size() > 0) {
                    advanceDrawActions.remove(advanceDrawActions.size() - 1);
                }
                StringBuilder text = new StringBuilder();
                for (String s : advanceDrawActions) {
                    text.append(s);
                }
                textField.setText(String.valueOf(text));
            }
        });
    }
}