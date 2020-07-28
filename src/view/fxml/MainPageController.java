package view.fxml;

import controller.Solver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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

public class MainPageController {
    private static MainPageController instance;

    public static MainPageController getInstance() {
        if (instance == null) {
            instance = new MainPageController();
        }
        return instance;
    }

    private static final Stage Stage = new Stage();

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TabPane tabPane;

    @FXML
    private TextArea fileTextArea;

    @FXML
    private TextArea errorTextArea;

    @FXML
    private Button simulate;

    @FXML
    private Button drawGraph;

    private File file;
    private String firstBackUpText;

    private final ImageView resistor = new ImageView(new Image("view/img/resistor-symbol.png"));
    private final ImageView capacitor = new ImageView(new Image("view/img/Symbol_Capacitor.png"));
    private final ImageView inductor = new ImageView(new Image("view/img/Inductor.png"));
    private final ImageView battery = new ImageView(new Image("view/img/battery.png"));

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
    }

    public void simulate() {
        errorTextArea.setText("Program is simulating ...\n" +
                "Please wait ...");
        if (file == null) {
          file = new File("newFile.txt");
        }
        updateFile();
        if (FileScanner.runProgram(file)) {
            errorTextArea.setText("File successfully simulated.\n" + String.valueOf(Solver.output));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Can not simulate the file!");
            alert.show();
            errorTextArea.setText(Errors.string + "\nERROR happened in simulation!");
        }
    }

    public void updateFile() {
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

    public void backToSave() {
        fileTextArea.setText(firstBackUpText);
    }

    public void getHelp() throws IOException {
        Stage helpStage = HelpPageController.getStage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("HelpPage.fxml"));
        Parent root = fxmlLoader.load();
        helpStage.setScene(new Scene(root));
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

    //  GETTERS AND SETTERS

    public static Stage getStage() {
        return Stage;
    }

}
