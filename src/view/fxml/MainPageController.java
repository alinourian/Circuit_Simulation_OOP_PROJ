package view.fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private TabPane tabPane;

    @FXML
    private TextArea fileTextArea;

    @FXML
    private TextArea errorTextArea;

    @FXML
    private Button simulate;

    private File file;

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
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File newFile = fileChooser.showOpenDialog(Stage);

        String text = String.valueOf(getTextOfFile(newFile));
        if (!text.equals("cancel")) {
            file = newFile;
            fileTextArea.setText(text);
            closeTabs();
        }
    }

    public void simulate() {
        updateFile();
        if (FileScanner.runProgram(file)) {
            closeTabs();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Can not simulate the file!");
            alert.show();
            errorTextArea.setText(Errors.string + "\nERROR happened in simulation!");
        }
    }

    public void updateFile() {
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

    private StringBuilder getTextOfFile(File file) {
        StringBuilder string = new StringBuilder();
        try {
            Scanner scanner = new Scanner(file);
            try {
                string.append(scanner.nextLine());
            } catch (RuntimeException e) {
                errorTextArea.setText(e + " Can not get the text of file" + "\n" + "File is Empty!");
            }
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

    public void printActions(String string) {
        String wholeText = errorTextArea.getText() + string;
        errorTextArea.setText(wholeText);
    }

    public void printResult(String result, boolean type) {
        errorTextArea.setText(result);
    }

    //  GETTERS AND SETTERS

    public static Stage getStage() {
        return Stage;
    }

}
