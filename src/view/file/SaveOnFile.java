package view.file;

import controller.InputController;
import model.Node;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public abstract class SaveOnFile {
    private static final File outputFile = new File("outputFile.txt");

    public static void saveDataOnFile() throws IOException {
        FileWriter fileWriter = new FileWriter(outputFile);
        ArrayList<Node> nodes = new ArrayList<>();

        for (int i = 1; i < InputController.getInstance().getNodes().size(); i++) {
            for (Node node : InputController.getInstance().getNodes()) {
                if (node.getName().equals(Integer.toString(i))) {
                    nodes.add(node);
                    break;
                }
            }
        }

        for (Node node : nodes) {
            fileWriter.write(node.getName() + " ");
            for (Double voltage : node.getVoltages()) {
                double saveVoltage = Math.floor(1000 * voltage) / 1000;
                fileWriter.write(saveVoltage + "\t");
            }
            fileWriter.write("\n");
        }
        fileWriter.close();
    }
}
