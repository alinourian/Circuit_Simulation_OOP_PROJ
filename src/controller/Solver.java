package controller;

import model.Element;
import model.Node;
import view.console.ConsoleScanner;
import view.console.ShowCircuit;

import java.util.HashMap;

public class Solver {
    private final InputController controller = InputController.getInstance();
    private static double time = 0;
    public static int step = 0;

    public static final HashMap<Double, Double> result = new HashMap<>();

    public void run() {
        Node groundNode = null;
        for (Node node : controller.getNodes()) {
            if (node.getName().equals("0")) {
                groundNode = node;
            }
        }
        if (groundNode == null) {
            System.err.println("ground not found!");
            return;
        }
        ShowCircuit.showInConsole();
        //String s = ConsoleScanner.getScanner().nextLine();
        do {

            System.out.printf("***(time = %.3f)***\n", time);
            //Node node = controller.findNode("1");
            //System.out.printf(node.getName() + "last voltage : %.2f\n", node.getVoltage());
            solve();
            //String s = ConsoleScanner.getScanner().nextLine();
            saveVoltages();
            saveCurrents();
            step++;
            time += controller.getDeltaT();
        } while (time <= controller.getTranTime());
    }

    public void solve() {
        int counter = 500;
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
                    //
                    //node.setVoltage(node.getVoltage() - controller.getDeltaV());
                    setBackElementCurrent();
                    //
                    node.setVoltage(node.getVoltage() - controller.getDeltaV());
                    double voltage = node.getVoltage() +
                            (Math.abs(totalCurrent1) - Math.abs(totalCurrent2)) / controller.getDeltaI()
                                    * controller.getDeltaV();
                    node.setVoltage(voltage);
                    updateElementsCurrent();
                    //System.out.printf("^^^" + node.getName() + "last voltage : %.2f\n", node.getVoltage());
                }
            }
            //System.out.println();
            //saveVoltages();
            updateElementsCurrent();
            counter--;
        } while (!checkKCL());
        //saveVoltages();
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
        //System.out.println("error: " + measurementError);
        return measurementError < Math.pow(10, -2);
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
            System.out.printf(node.getName() + " => voltage : %.3f\n", node.getVoltage());
        }
    }

}
