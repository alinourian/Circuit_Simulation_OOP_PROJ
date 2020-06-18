package controller;

import model.Element;
import model.Node;

public class Solver {
    private final InputController controller = InputController.getInstance();
    private double time = 0;
    private int step = 0;
    public void run() {
        for (Node node : controller.getNodes()) {
            System.out.println(node.getName() + " : " + node.getVoltage());
        }
        System.out.println("_______________*****_______________");
        start();
    }

    public void start() {
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
        do {
            step++;
            time += controller.getDeltaT();
            System.out.println("time = " + time);
            for (Node node : controller.getNodes()) {
                if (!node.getName().equals("0")) {
                    updateElementsCurrent();
                    double totalCurrent1 = node.getTotalCurrent(time);
                    //System.out.println("totalCurrent1 = " + totalCurrent1);
                    node.setVoltage(node.getVoltage() + controller.getDeltaV());
                    updateElementsCurrent();

                    double totalCurrent2 = node.getTotalCurrent(time);
                    //System.out.println("totalCurrent2 = " + totalCurrent2);
                    //
                    node.setVoltage(node.getVoltage() - controller.getDeltaV());
                    updateElementsCurrent();
                    //
                    double voltage = node.getVoltage() +
                            (Math.abs(totalCurrent1) - Math.abs(totalCurrent2)) / controller.getDeltaI()
                                    * controller.getDeltaV();
                    node.setVoltage(voltage);
                    System.out.printf(node.getName() + "last voltage : %.2f\n", node.getVoltage());
                }
            }
            System.out.println();
        } while (!checkKCL());
        printVoltages();
    }

    private boolean checkKCL() {
        double measurementError = 0;
        for (Node node : controller.getNodes()) {
            measurementError += Math.pow(node.getTotalCurrent(time), 2);
        }
        //System.out.println("error : " + measurementError);
        return measurementError < Math.pow(10, -5);
    }

    private void updateElementsCurrent() {
        for (Element element : controller.getElements()) {
            element.updateElementCurrent();
        }
    }

    private void printVoltages() {
        for (Node node : controller.getNodes()) {
            System.out.printf(node.getName() + " => voltage : %.2f\n", node.getVoltage());
        }
    }

}
