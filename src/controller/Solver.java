package controller;

import model.Element;
import model.Node;
import view.Errors;
import view.console.ShowCircuit;
import view.file.SaveOnFile;
import view.fxml.Main;
import view.fxml.MainPageController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Solver {
    private final InputController controller = InputController.getInstance();
    public static double time = 0;
    public static int step = 0;
    public static int measureErrorEachStep = 0;
    public static StringBuilder output = new StringBuilder();
    public ArrayList<Integer> errors = new ArrayList<>();

    public boolean run() {
        Node groundNode = null;
        for (Node node : controller.getNodes()) {
            if (node.getName().equals("0")) {
                groundNode = node;
            }
        }
        if (groundNode == null) {
            Errors.groundError();
            return false;
        }
        do {
            double _time = ((double) Math.round(time * 1000))/1000;
            Errors.print("\n***(time = " + _time + ")***");
            output.append("\n***(time = ").append(_time).append(")***");

            solve();
            errors.add(measureErrorEachStep);
            if (measureErrorEachStep > 50000) {
                Errors.transitionFailed();
                return false;
            }

            saveVoltages();
            saveCurrents();
            step++;
            time += controller.getDeltaT();
        } while (time <= controller.getTranTime());

        try {
            SaveOnFile.saveDataOnFile();
        } catch (IOException e) {
            Errors.saveFileError(e);
            return false;
        }
        int max = 0;
        for (Integer error : errors) {
            max = Math.max(max, error);
        }
        System.out.println(max);
        return true;
    }

    public void solve() {
        measureErrorEachStep = 0;
        do {
            for (Node node : controller.getNodes()) {
                if (!node.getName().equals("0")) {
                    //updateElementsCurrent();
                    double totalCurrent1 = node.getTotalCurrent(time);
                    //System.out.println("totalCurrent1 = " + totalCurrent1);
                    node.setVoltage(node.getVoltage() + controller.getDeltaV());
                    updateElementsCurrent();
                    double totalCurrent2 = node.getTotalCurrent(time);
                    //System.out.println("totalCurrent2 = " + totalCurrent2);
                    setBackElementCurrent();

                    node.setVoltage(node.getVoltage() - controller.getDeltaV());
                    double voltage = node.getVoltage() +
                            (Math.abs(totalCurrent1) - Math.abs(totalCurrent2)) / controller.getDeltaI()
                                    * controller.getDeltaV();
                    node.setSaveVoltage(voltage);
                    //node.setVoltage(voltage);
                    //updateElementsCurrent();
                }
            }
            //System.out.println();
            setVoltages();
            updateElementsCurrent();
            measureErrorEachStep++;
        } while (!checkKCL() && measureErrorEachStep <= 50000);
        //updateElementsCurrent();
        printVoltages();
    }

    private void setVoltages() {
        for (Node node : controller.getNodes()) {
            node.setVoltage(node.getSaveVoltage());
        }
    }

    private boolean checkKCL() {
        double measurementError = 0;
        for (Node node : controller.getNodes()) {
            measurementError += Math.abs(node.getTotalCurrent(time));
        }
        System.out.println("error: " + measurementError);
        return measurementError < Math.pow(10, -2);
    }

    private double getKCLError() {
        double measurementError = 0;
        for (Node node : controller.getNodes()) {
            measurementError += Math.abs(node.getTotalCurrent(time));
        }
        //System.out.println("error: " + measurementError);
        return ((double) Math.round(measurementError * Math.pow(10, 10))) / Math.pow(10, 10);
    }

    private void updateElementsCurrent() {
        for (Element element : controller.getElements()) {
            element.updateElementCurrent();

        }

    }

    private void setBackElementCurrent() {
        for (Element element : controller.getElements()) {
            element.setBackElementCurrent();
        }
    }

    private void saveVoltages() {
        for (Node node : controller.getNodes()) {
            node.getVoltages().add(node.getVoltage());
        }
    }

    private void saveCurrents() {
        for (Element element : controller.getElements()) {
            element.getCurrents().add(element.getCurrent(element.getNodeP()));
        }
    }

    private void printVoltages() {
        for (Node node : controller.getNodes()) {
            //System.out.printf(node.getName() + " => voltage : %.3f\n", node.getVoltage());
            double _voltage = ((double) Math.round(node.getVoltage() * 1000))/1000;
            Errors.print(node.getName() + " => voltage : " + _voltage);
            Solver.output.append(" => voltage node ").append(node.getName()).append(" : ").append(_voltage);
        }
    }

}
