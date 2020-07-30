package view.fxml;

import controller.Solver;
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
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import view.Errors;
import view.file.FileScanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainPageController {
    private static MainPageController instance;

    public static MainPageController getInstance() {
        if (instance == null) {
            instance = new MainPageController();
        }
        return instance;
    }

    private static final Stage Stage = new Stage();

    @FXML private TabPane tabPane;
    @FXML private TextArea fileTextArea;
    @FXML private TextArea errorTextArea;
    @FXML private ProgressBar progressBar;
    @FXML private Label percentLabel;
    @FXML private Pane circuitPane;

    private File file;
    private String firstBackUpText;
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private double drawCircuitStep;

    private final Image resistor = new Image("view/img/element/Resistor.png");
    private final Image capacitor = new Image("view/img/element/Capacitor.png");
    private final Image inductor = new Image("view/img/element/Inductor.png");
    private final Image diode = new Image("view/img/element/Diode.png");
    private final Image gnd = new Image("view/img/element/Ground.png");
    private final Image vSource = new Image("view/img/element/VSource.png");
    private final Image cSource = new Image("view/img/element/CSource.png");
    private final Image acSource = new Image("view/img/element/AcSource.png");
    private final Image controlledVSource = new Image("view/img/element/ControlledVSource.png");
    private final Image controlledCSource = new Image("view/img/element/ControlledCSource.png");
    private final Image wire = new Image("view/img/element/Wire.png");

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

    public void chooseFile() {
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

    public void simulate() {
        errorTextArea.setText("");
        simulating();
        Task displayMessage = new Task<Void>() {
            @Override
            public Void call() {
                Platform.runLater(() -> {
                    if (file == null) {
                        file = new File("newFile.txt");
                    }
                    updateFile();
                    //percentLabel.setText("40%");
                    //progressBar.setProgress(-1);

                    if (FileScanner.runProgram(file)) {
                        drawCircuit();
                        errorTextArea.setText("File successfully simulated.\n" + Solver.output);
                        percentLabel.setText("100%");
                        progressBar.setProgress(1);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Can not simulate the file!");
                        alert.show();
                        errorTextArea.setText(Errors.string + "\nERROR happened in simulation!");
                        percentLabel.setText("ERROR");
                        progressBar.setProgress(0);
                    }
                });
                return null;
            }
        };
        executor.execute(displayMessage);
    }

    private void simulating() {
        errorTextArea.setText("Program is simulating ...\n" +
                "Please wait ...");
        progressBar.setVisible(true);
        percentLabel.setVisible(true);
        percentLabel.setText("0%");
        progressBar.setProgress(-1);
        /*
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {
        }
         */
    }

    private void updateFile() {
        closeTabs();
        saveFile();
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

    public void undoToSave() {
        fileTextArea.setText(firstBackUpText);
    }

    public void showPrintPreview() throws IOException {
        if (!tabPane.getSelectionModel().isSelected(0)) {
            NewTab tab = (NewTab) tabPane.getSelectionModel().getSelectedItem();
            tab.showGraph();
        } else {
            Stage helpStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ShowCircuitPage.fxml"));
            Parent root = fxmlLoader.load();
            helpStage.setScene(new Scene(root));
            helpStage.setTitle("Show Circuit");
            helpStage.getIcons().add(Main.stageIcon);
            ShowCircuitPage controller = fxmlLoader.getController();
            controller.initial(circuitPane);
            helpStage.show();
        }
    }

    public void getHelp() throws IOException {
        Stage helpStage = HelpPageController.getStage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("HelpPage.fxml"));
        Parent root = fxmlLoader.load();
        helpStage.setScene(new Scene(root));
        helpStage.setTitle(Main.stageTitle);
        helpStage.getIcons().add(Main.stageIcon);
        helpStage.setResizable(false);
        helpStage.show();
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

    public void Exit() {
        Stage.close();
    }

    public void drawCircuit() {
        drawCircuitStep = Math.min(circuitPane.getWidth() / 7, circuitPane.getHeight() / 6);
        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 5; j++) {
                circuitPane.getChildren().add(new Circle(getXY(i), getXY(j), 1));
            }
        }
        putImage(resistor, 9, 3);
        putImage(capacitor, 10, 4);
        putImage(cSource, 11, 5);
        putImage(inductor, 12, 11);
        putImage(controlledCSource, 12, 6);
        putImage(wire, 9, 10);
        putImage(wire, 10, 11);
        putImage(wire, 3, 4);
        putImage(wire, 4, 5);
        putImage(diode, 5, 6);
        putImage(acSource, 9, 8);
        putImage(controlledVSource, 7, 8);
        putImage(wire, 7, 1);
        putImage(wire, 1, 2);
        putImage(vSource, 2, 3);
    }

    private PaneNode convertNodeNumToPaneNode(double num) {
        double x = num % 6 != 0 ? num % 6 : 6;
        double y = 6 - Math.ceil(num / 6 );
        return getNode(x, y);
    }

    private double getXY(double XY) {
        return XY * drawCircuitStep;
    }


    private PaneNode getNode(double x, double y) {
        return new PaneNode(getXY(x), getXY(y));
    }

    private PaneNode getLayoutNode(PaneNode middlePoint) {
        return new PaneNode(middlePoint.nodeX - drawCircuitStep / 2, middlePoint.nodeY - drawCircuitStep / 2);
    }

    private void putImage(Image image, double numNode1, double numNode2) {
        PaneNode node1 = convertNodeNumToPaneNode(numNode1);
        PaneNode node2 = convertNodeNumToPaneNode(numNode2);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(drawCircuitStep);
        imageView.setFitHeight(drawCircuitStep);
        if (node1.nodeX == node2.nodeX) {
            PaneNode middlePoint = new PaneNode(node1.nodeX, (node1.nodeY + node2.nodeY) / 2);
            PaneNode node = getLayoutNode(middlePoint);
            imageView.setLayoutX(node.nodeX);
            imageView.setLayoutY(node.nodeY);
            circuitPane.getChildren().add(imageView);
        } else if (node1.nodeY == node2.nodeY) {
            imageView.setRotate(90);
            PaneNode middlePoint = new PaneNode((node1.nodeX + node2.nodeX) / 2, node1.nodeY);
            PaneNode node = getLayoutNode(middlePoint);
            imageView.setLayoutX(node.nodeX);
            imageView.setLayoutY(node.nodeY);
            circuitPane.getChildren().add(imageView);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Not Possible!");
            alert.show();
        }
    }

    //  GETTERS AND SETTERS

    public static Stage getStage() {
        return Stage;
    }

}

class PaneNode {
    double nodeX;
    double nodeY;

    public PaneNode(double nodeX, double nodeY) {
        this.nodeX = nodeX;
        this.nodeY = nodeY;
    }

    @Override
    public String toString() {
        return "PaneNode{" +
                "nodeX=" + nodeX +
                ", nodeY=" + nodeY +
                '}';
    }
}