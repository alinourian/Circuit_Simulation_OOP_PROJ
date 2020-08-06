package view.fxml;

import controller.InputController;
import controller.Solver;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Branch;
import model.Circuit;
import model.Element;
import model.Source;
import view.Errors;
import view.file.FileScanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainPageController {

    private static final Stage Stage = new Stage();

    @FXML private TabPane tabPane;
    @FXML private TextArea fileTextArea;
    @FXML private TextArea errorTextArea;
    @FXML private ProgressBar progressBar;
    @FXML private Label percentLabel;
    @FXML private BorderPane borderPane;

    public static boolean simulateStop = false;

    private File file;
    private String firstBackUpText;
    private final ExecutorService executor = Executors.newCachedThreadPool();

    private final long PERIOD = 200_000_000L;
    private long timer = -1;
    private AnimationTimer animationTimer;
    private double additional = 0;
    private String statusKCLError = "10m";

    // MENU OPTIONS && TOOLBAR OPTIONS

    public void addNewTab() {
        if (FileScanner.hasFile) {
            Tab newTab = new NewTab("New Tab  ");
            tabPane.getTabs().add(newTab);
            tabPane.getSelectionModel().select(newTab);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("File is not OK! Please open or simulate your file first.");
            alert.show();
        }
    }

    public void newFile() {
        resetPage();
    }

    public void openFile() {
        FileScanner.hasFile = false;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File newFile = fileChooser.showOpenDialog(Stage);

        String text = String.valueOf(getTextOfFile(newFile));
        firstBackUpText = text;
        if (!text.equals("cancel")) {
            fileTextArea.setEditable(true);
            file = newFile;
            fileTextArea.setText(text);
            closeTabs();
        }
        errorTextArea.setText("");
        progressBar.setVisible(false);
        percentLabel.setVisible(false);
        percentLabel.setText("0%");
        progressBar.setProgress(0);
    }

    public void saveFile() {
        if (file != null) {
            try {
                FileWriter fileWriter = new FileWriter(file);
                Scanner scanner = new Scanner(fileTextArea.getText());
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    //System.out.println(line);
                    fileWriter.write(line + "\n");
                }
                scanner.close();
                fileWriter.close();
            } catch (IOException | RuntimeException e) {
                Errors.exceptionsError(e);
                errorTextArea.setText(Errors.string + "\nCan not save the file!");
            }
        }
        firstBackUpText = String.valueOf(getTextOfFile(file));
    }

    public void saveNewFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.setInitialFileName("myFile");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text File", "*.txt"));
        File fileSelected = fileChooser.showSaveDialog(getStage());
        fileChooser.setInitialDirectory(fileSelected.getParentFile());
        try {
            FileWriter fileWriter = new FileWriter(fileSelected);
            fileWriter.write(fileTextArea.getText());
            fileWriter.close();
        } catch (IOException ignored) {
        }
    }

    public void print() {
        //TODO
    }

    public void showPrintPreview() throws IOException {
        if (!tabPane.getSelectionModel().isSelected(0)) {
            NewTab tab = (NewTab) tabPane.getSelectionModel().getSelectedItem();
            tab.showGraph();
        } else {
            Stage helpStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("showCircuitPage.fxml"));
            Parent root = fxmlLoader.load();
            helpStage.setScene(new Scene(root));
            helpStage.setTitle("Show Circuit");
            helpStage.getIcons().add(Main.stageIcon);
            ShowCircuitPage controller = fxmlLoader.getController();
            controller.initial(new Pane());
            helpStage.show();
        }
    }

    public void setting() {
        HashMap<String, Double> results = new HashMap<>();
        results.put("100m", 0.1);results.put("10m", 0.01);
        results.put("1m", 0.001); results.put("100u", 0.0001);
        results.put("10u", 0.00001); results.put("1u", 0.000001);
        ArrayList<String> list = new ArrayList<>();
        list.add("100m"); list.add("10m"); list.add("1m");
        list.add("100u"); list.add("10u"); list.add("1u");

        ChoiceDialog<String> dialog = new ChoiceDialog<>(statusKCLError, list);
        dialog.setTitle("Setting");
        dialog.setHeaderText("");
        Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
        dialogStage.getIcons().add(new Image("view/img/setting-icon.png"));
        dialog.setContentText("Choose KCL_ERROR:");
        ImageView imageView = new ImageView(new Image("view/img/setting-icon.png"));
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        dialog.setGraphic(imageView);

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            Solver.setKCL_ERROR(results.get(result.get()));
            statusKCLError = result.get();
            System.out.println("KCL ERROR set at : " + Solver.getKclError());
        }

    }

    public void Exit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("");
        alert.setContentText("Are you sure you want to exit?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            Stage.close();
        }
    }

    public void undoToSave() {
        fileTextArea.setText(firstBackUpText);
    }

    public void cut() {
        //TODO
    }

    public void copy() {
        //TODO
    }

    public void paste() {
        //TODO
    }

    public void delete() {
        resetPage();
    }

    public void zoomIn() {
        //TODO
    }

    public void zoomOut() {
        //TODO
    }

    public void getHelp() throws IOException {
        Stage helpStage = HelpPageController.getStage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("helpPage.fxml"));
        Parent root = fxmlLoader.load();
        helpStage.setScene(new Scene(root));
        helpStage.setTitle(Main.stageTitle);
        helpStage.getIcons().add(Main.stageIcon);
        helpStage.setResizable(false);
        helpStage.show();
    }

    public void simulate() {
        simulateStop = false;
        errorTextArea.setText("");
        simulating();
        Task displayMessage = new Task<Void>() {
            @Override
            public Void call() {
                Platform.runLater(() -> {
                    if (file == null) {
                        file = new File("newFile.txt");
                    }
                    fillProgress();
                    updateFile();

                    if (FileScanner.runProgram(file)) {
                        additional = 0.1;
                        animationTimer.stop();
                        errorTextArea.setText("File successfully simulated.\n" + Solver.output);
                        percentLabel.setText("100%");
                        progressBar.setProgress(1);
                        Errors.print("KCL ERROR : " + Solver.getKclError());
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Can not simulate the file!");
                        alert.show();
                        animationTimer.stop();
                        errorTextArea.setText(Errors.string + "\nERROR happened in simulation!");
                        percentLabel.setText("ERROR");
                        progressBar.setProgress(0);
                    }

                    drawCircuit();

                    timer = -1;
                    additional = 0;
                });
                return null;
            }
        };
        executor.execute(displayMessage);
    }

    public void stopSimulating() {
        simulateStop = false;
    }

    public void drawVCPGraphs() {
        if (!FileScanner.hasFile) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please simulate your file first.");
            alert.show();
            return;
        }
        ArrayList<String> choices = new ArrayList<>();
        for (Element element : InputController.getInstance().getElements()) {
            choices.add(element.getName());
        }
        for (Source source : InputController.getInstance().getSources()) {
            choices.add(source.getName());
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(
                InputController.getInstance().getElements().get(0).getName(), choices);
        dialog.setTitle("Choice Dialog");
        dialog.setHeaderText("Draw Voltage/Current/Power Graph");
        dialog.setContentText("Choose your element :");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            Element element = null;
            Source source = null;
            for (Element temp : InputController.getInstance().getElements()) {
                if (temp.getName().equals(result.get())) {
                    element = temp;
                }
            }
            if (element == null) {
                for (Source temp : InputController.getInstance().getSources()) {
                    if (temp.getName().equals(result.get())) {
                        source = temp;
                    }
                }
                NewTab voltageTab = new NewTab("NewTab  ");
                assert source != null;
                voltageTab.drawCustomChart(NewTab.subCharts(source.getNodeP().getVoltages(),
                        source.getNodeN().getVoltages()), "(Node " + source.getNodeP().getName() + ") -" +
                        "(Node " + source.getNodeN().getName() + ")", "Voltage");
                NewTab currentTab = new NewTab("NewTab  ");
                currentTab.drawCustomChart(source.getCurrents(), source.getName(), "Current");
                NewTab powerTab = new NewTab("NewTab  ");
                powerTab.drawCustomChart(NewTab.multiplyCharts(source.getCurrents(),
                        NewTab.subCharts(source.getNodeP().getVoltages(), source.getNodeN().getVoltages())),
                        source.getName(), "Power");
                tabPane.getTabs().addAll(voltageTab, currentTab, powerTab);
                tabPane.getSelectionModel().select(powerTab);
            } else {
                NewTab voltageTab = new NewTab("NewTab  ");
                voltageTab.drawCustomChart(NewTab.subCharts(element.getNodeP().getVoltages(),
                        element.getNodeN().getVoltages()), "(Node " + element.getNodeP().getName() + ") -" +
                        "(Node " + element.getNodeN().getName() + ")", "Voltage");
                NewTab currentTab = new NewTab("NewTab  ");
                currentTab.drawCustomChart(element.getCurrents(), element.getName(), "Current");
                NewTab powerTab = new NewTab("NewTab  ");
                powerTab.drawCustomChart(NewTab.multiplyCharts(element.getCurrents(),
                        NewTab.subCharts(element.getNodeP().getVoltages(), element.getNodeN().getVoltages())),
                        element.getName(), "Power");
                tabPane.getTabs().addAll(voltageTab, currentTab, powerTab);
                tabPane.getSelectionModel().select(powerTab);
            }
        }
    }

    public void drawCircuit() {
        //Alert alert = new Alert(Alert.AlertType.ERROR);
        //alert.setContentText("not yet!");
        //alert.show();
        Pane pane = DrawCircuit.drawCircuit();
        if (!borderPane.getChildren().contains(pane))
        {
            borderPane.getChildren().add(pane);
        } else {
            borderPane.getChildren().clear();
            borderPane.getChildren().add(pane);
        }

        //TODO
    }

    // ***** HELPING METHODS *****

    private void simulating() {
        errorTextArea.setText("Program is simulating ...\n" +
                "Please wait ...");
        progressBar.setVisible(true);
        percentLabel.setVisible(true);
        percentLabel.setText("0%");
        progressBar.setProgress(0);
    }

    public void fillProgress() {
        progressBar.setVisible(true);
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (timer == -1) {
                    timer = now;
                    progressBar.setProgress(0);
                } else if (now - timer > PERIOD) {
                    if (additional == 0.1 && progressBar.getProgress() + additional <= 1) {
                        progressBar.setProgress(progressBar.getProgress() + additional);
                        timer = now;
                        double percent = progressBar.getProgress() * 100;
                        percentLabel.setText(percent + "%");
                    }
                }
            }
        };
        animationTimer.start();
    }

    private void updateFile() {
        closeTabs();
        saveFile();
    }

    private StringBuilder getTextOfFile(File file) {
        StringBuilder string = new StringBuilder();
        try {
            Scanner scanner = new Scanner(file);
            string.append(scanner.nextLine());
            while (scanner.hasNextLine()) {
                string.append("\n");
                String line = scanner.nextLine();
                string.append(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            errorTextArea.setText(e + " Scanner got problem" + "\n" + "File does not exist!");
        } catch (RuntimeException e) {
            return new StringBuilder("cancel");
        }
        return string;
    }

    private void closeTabs() {
        ArrayList<Tab> tabsForRemove = new ArrayList<>();
        for (Tab tab : tabPane.getTabs()) {
            if (tab.isClosable()) {
                tabsForRemove.add(tab);
            }
        }
        for (Tab tab : tabsForRemove) {
            tabPane.getTabs().remove(tab);
        }
    }

    private void resetPage() {
        FileScanner.hasFile = false;
        closeTabs();
        fileTextArea.setText("");
        errorTextArea.setText("");
        InputController.getInstance().restartProgram();
    }

//  GETTERS AND SETTERS

    public static Stage getStage() {
        return Stage;
    }


}

