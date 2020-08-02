package controller;

import model.Element;
import model.Node;
import model.Union;
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
    private final int MAX_ERROR_MEASUREMENT_TRYING = 500000;
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

        UnionCreator unionCreator = new UnionCreator();
        unionCreator.run();


        do {
            step++;
            time += controller.getDeltaT();

            double _time = ((double) Math.round(time * 1000))/1000;
            Errors.print("\n***(time = " + _time + ")***");
            output.append("\n***(time = ").append(_time).append(")***");

            solve3();


            errors.add(measureErrorEachStep);

            if (measureErrorEachStep > MAX_ERROR_MEASUREMENT_TRYING) {
                System.out.println("out...");
                Errors.transitionFailed();
                return false;
            }

            saveVoltages();
            saveCurrents();

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


    public void solve(){

        measureErrorEachStep = 0;

        do {

            for (Union union : controller.getUnions())
            {
                if (union.getType().equals("SingleNode"))
                {
                    if (!union.getFatherOfUnion().getName().equals("0"))
                    {
                      //  System.out.println("single");
                        double totalCurrent1 = union.getTotalCurrent();
                        double totalCurrent2;

                        union.getFatherOfUnion().setVoltage( union.getFatherOfUnion().getVoltage() + controller.getDeltaV());
                        updateElementsCurrent();
                        double tempTotalCurrentPlus = union.getTotalCurrent();
                        setBackElementCurrent();

                        union.getFatherOfUnion().setVoltage( union.getFatherOfUnion().getVoltage() - 2*controller.getDeltaV());
                        updateElementsCurrent();
                        double tempTotalCurrentMinus = union.getTotalCurrent();
                        setBackElementCurrent();

                        union.getFatherOfUnion().setVoltage( union.getFatherOfUnion().getVoltage() + controller.getDeltaV());


                        if (Math.abs(tempTotalCurrentMinus) >= Math.abs(tempTotalCurrentPlus))
                            totalCurrent2 = tempTotalCurrentPlus;
                        else
                            totalCurrent2 = tempTotalCurrentMinus;

                        /*System.out.println("union : "+union.getName());
                        System.out.println("total cur 1: "+totalCurrent1);
                        System.out.println("total cur 2: "+totalCurrent2);
*/                        /*System.out.println("tem cur plus: "+tempTotalCurrentPlus);
                        System.out.println("tem cur minus: "+tempTotalCurrentMinus);*/


                        double voltage = union.getFatherOfUnion().getVoltage() +
                                ( Math.abs(totalCurrent1) - Math.abs(totalCurrent2) ) / controller.getDeltaI() * controller.getDeltaV();

                    //    System.out.println("voltage is: "+voltage);

                        union.getFatherOfUnion().setSaveVoltage(voltage);
                    }
                }
                else
                {
                    if (!union.getFatherOfUnion().getName().equals("0"))
                    {
                        System.out.println("multi");
                        double totalCurrent1 = union.getTotalCurrent();
                        double totalCurrent2;

                        union.getFatherOfUnion().setVoltage( union.getFatherOfUnion().getVoltage() + controller.getDeltaV());
                        System.out.println("union name is: "+union.getName());
                        System.out.println("union father is: "+ union.getFatherOfUnion().getName());
                        union.updateNodesVoltages();

                        updateElementsCurrent();
                        double tempTotalCurrentPlus = union.getTotalCurrent();
                        setBackElementCurrent();

                        union.getFatherOfUnion().setVoltage( union.getFatherOfUnion().getVoltage() - 2*controller.getDeltaV());
                        union.updateNodesVoltages();
                        updateElementsCurrent();
                        double tempTotalCurrentMinus = union.getTotalCurrent();
                        setBackElementCurrent();

                        union.getFatherOfUnion().setVoltage( union.getFatherOfUnion().getVoltage() + controller.getDeltaV());
                        union.updateNodesVoltages();

                        if (Math.abs(tempTotalCurrentMinus) >= Math.abs(tempTotalCurrentPlus))
                            totalCurrent2 = tempTotalCurrentPlus;
                        else
                            totalCurrent2 = tempTotalCurrentMinus;


                        double voltage = union.getFatherOfUnion().getVoltage() +
                                ( Math.abs(totalCurrent1) - Math.abs(totalCurrent2) ) / controller.getDeltaI() * controller.getDeltaV();

                        union.getFatherOfUnion().setSaveVoltage(voltage);
                    }
                }
            }

            setVoltages();

            for (Union union : controller.getUnions()) {
                union.updateNodesVoltages();
            }


            updateElementsCurrent();
            measureErrorEachStep++;

        }while (!checkKCL() && measureErrorEachStep <= MAX_ERROR_MEASUREMENT_TRYING);

        printVoltages();

    }

    /////////////////////////////////////////////////////
    ////// this method doesn't workout correct //////////
    /////////////////////////////////////////////////////
    public void solve3() {
        measureErrorEachStep = 0;
        do {
            for (Node node : controller.getNodes()) {
                if (!node.getName().equals("0")) {
                    //updateElementsCurrent();
                    double totalCurrent1 = node.getTotalCurrent();
                    //System.out.println("totalCurrent1 = " + totalCurrent1);
                    node.setVoltage(node.getVoltage() + controller.getDeltaV());
                    updateElementsCurrent();
                    double totalCurrent2 = node.getTotalCurrent();
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
        } while (!checkKCL() && measureErrorEachStep <= 75000);
        //updateElementsCurrent();
        printVoltages();
    }

    private void setVoltages() {
        for (Node node : controller.getNodes()) {
            node.setVoltage(node.getSaveVoltage());
        }
    }

    private boolean checkKCL()
    {
        double measurementError = 0;

        for (Union union : controller.getUnions()) {
            measurementError += Math.abs(union.getTotalCurrent());
            System.out.println("total current of union: "+union.getName()+" is "+Math.abs(union.getTotalCurrent()));
        }
        return measurementError < Math.pow(10,-1);


    }

    /////////////////////////////////////////////////////
    ////// this method doesn't workout correct //////////
    /////////////////////////////////////////////////////
    private boolean checkKCL3() {
        double measurementError = 0;
        for (Node node : controller.getNodes()) {
            measurementError += Math.abs(node.getTotalCurrent());
            System.out.println("total current of node: "+node.getName()+" is "+Math.abs(node.getTotalCurrent()));
        }
        //System.out.println("error: " + measurementError);
        return measurementError < 9*Math.pow(10, -2);
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
    }

    private void printVoltages() {
        for (Node node : controller.getNodes()) {
            double _voltage = ((double) Math.round(node.getVoltage() * 1000))/1000;
            Errors.print(node.getName() + " => voltage : " + _voltage);
            Solver.output.append("\n=> voltage node ").append(node.getName()).append(" : ").append(_voltage);
        }
    }

}
