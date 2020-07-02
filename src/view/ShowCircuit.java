package view;

import controller.InputController;
import model.Element;
import model.Source;

public abstract class ShowCircuit {
    private static final InputController controller = InputController.getInstance();

    public static void showInConsole() {
        for (Source source : controller.getSources()) {
            System.out.println(source);
        }
        for (Element element : controller.getElements()) {
            System.out.println(element);
        }
    }
}
