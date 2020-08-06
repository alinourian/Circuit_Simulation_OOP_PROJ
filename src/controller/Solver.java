package controller;

import model.*;
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
    private final int MAX_ERROR_MEASUREMENT_TRYING = 5_000_000;
    private static double KCL_ERROR = Math.pow(10, -2);
    public static StringBuilder output = new StringBuilder();
    public ArrayList<Integer> errors = new ArrayList<>();

    public boolean run() {
        Node groundNode = null;
        for (Node node : controller.getNodes()) {
            if (node.getName().equals("0")) {
                groundNode = node;
                controller.setGround(node);
            }
        }
        if (groundNode == null) {
            Errors.groundError();
            return false;
        }

        UnionCreator unionCreator = new UnionCreator();
        unionCreator.run();

        do {
            if (MainPageController.simulateStop) {
                Errors.stopImmediately();
                return false;
            }

            double _time = ((double) Math.round(time * 10000))/10000;
            //Errors.print("\n***(time = " + _time + ")***");
            output.append("\n***(time = ").append(_time).append(")***");

            if (!solve()) {
                return false;
            }

            errors.add(measureErrorEachStep);

            if (measureErrorEachStep > MAX_ERROR_MEASUREMENT_TRYING) {
                if (!checkError2()) {
                    return false;
                }
                Errors.transitionFailed();
                return false;
            }

            saveVoltages();
            saveCurrents();

            step++;
            time += controller.getDeltaT();
            System.out.println(time);

        } while (time <= controller.getTranTime() + Math.pow(10, -7));

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

    public boolean solve() {
        measureErrorEachStep = 0;

        do {

            for (Union union : controller.getUnions())
            {
                if (union.getType().equals("SingleNode"))
                {
                    if (!union.getFatherOfUnion().getName().equals("0"))
                    {
                        double totalCurrent1 = union.getTotalCurrent();

                        union.getFatherOfUnion().setVoltage( union.getFatherOfUnion().getVoltage() + controller.getDeltaV());
                        updateElementsCurrent();
                        double totalCurrent2 = union.getTotalCurrent();
                        setBackElementCurrent();

                        union.getFatherOfUnion().setVoltage( union.getFatherOfUnion().getVoltage() - controller.getDeltaV());

                        double voltage = union.getFatherOfUnion().getVoltage() +
                                ( Math.abs(totalCurrent1) - Math.abs(totalCurrent2) ) / controller.getDeltaI() * controller.getDeltaV();

                        union.getFatherOfUnion().setSaveVoltage(voltage);
                    }
                }
                else
                {
                    if (!union.getFatherOfUnion().getName().equals("0"))
                    {
                        //System.out.println("multi");
                        double totalCurrent1 = union.getTotalCurrent();

                        union.getFatherOfUnion().setVoltage( union.getFatherOfUnion().getVoltage() + controller.getDeltaV());
                        //System.out.println("union name is: "+union.getName());
                        //System.out.println("union father is: "+ union.getFatherOfUnion().getName());
                        if (!union.updateNodesVoltages()) {
                            return false;
                        }

                        updateElementsCurrent();
                        double totalCurrent2 = union.getTotalCurrent();
                        setBackElementCurrent();

                        union.getFatherOfUnion().setVoltage( union.getFatherOfUnion().getVoltage() - controller.getDeltaV());
                        if (!union.updateNodesVoltages()) {
                            return false;
                        }

                        double voltage = union.getFatherOfUnion().getVoltage() +
                                ( Math.abs(totalCurrent1) - Math.abs(totalCurrent2) ) / controller.getDeltaI() * controller.getDeltaV();

                        union.getFatherOfUnion().setSaveVoltage(voltage);
                    }
                }
            }

            setVoltages();

            for (Union union : controller.getUnions()) {
                if (!union.updateNodesVoltages()) {
                    return false;
                }
            }

            updateElementsCurrent();
            measureErrorEachStep++;

        }while (!checkKCL() && measureErrorEachStep <= MAX_ERROR_MEASUREMENT_TRYING);

        printVoltages();

        return true;
    }

    private void setVoltages() {
        for (Node node : controller.getNodes()) {
            node.setVoltage(node.getSaveVoltage());
        }
    }

    private boolean checkKCL() {
        double measurementError = 0;

        for (Union union : controller.getUnions()) {
            measurementError += Math.abs(union.getTotalCurrent());
            //System.out.println("total current of union: "+union.getName()+" is "+Math.abs(union.getTotalCurrent()));
        }
        return measurementError < KCL_ERROR;
    }

    private double getKCLError() {
        double measurementError = 0;
        for (Node node : controller.getNodes()) {
            measurementError += Math.abs(node.getTotalCurrent());
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
        for (Source source : controller.getSources()) {
            if (source instanceof VoltageSource) {
                VoltageSource vSource = (VoltageSource) source;
                source.getCurrents().add(vSource.getCurrentForVSource(vSource.getNodeP(), vSource.getNodeN()));
            }else if (source instanceof CurrentSource) {
                source.getCurrents().add(source.getCurrent(source.getNodeP()));
            }
        }
    }

    private void printVoltages() {
        for (Node node : controller.getNodes()) {
            double _voltage = ((double) Math.round(node.getVoltage() * 1000))/1000;
            //Errors.print(node.getName() + " => voltage : " + _voltage);
            Solver.output.append("\n=> voltage node ").append(node.getName()).append(" : ").append(_voltage);
        }
    }

    private boolean checkError2() {
        for (Node node : controller.getNodes()) {
            if (node.getTotalCurrent() > KCL_ERROR) {
                if (node.getElements().size() > 0) {
                    return true;
                }
                for (Source source : node.getSources()) {
                    if (source instanceof VoltageSource) {
                        return true;
                    }
                }
                Errors.errors(-2, "Currents conflicted!");
                return false;
            }
        }
        return true;
    }

    public static double getKclError() {
        return KCL_ERROR;
    }

    public static void setKCL_ERROR(double KCL_ERROR) {
        Solver.KCL_ERROR = KCL_ERROR;
    }
}
