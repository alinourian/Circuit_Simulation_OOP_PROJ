package controller;

import model.Node;
import view.Errors;

public class CircuitErrorHandling {

    private static final InputController controller = InputController.getInstance();

    public static boolean errors() {
        return error1() && error2() && error3() && error4() && error5();
    }

    private static boolean error1() {
        return true;
    }

    private static boolean error2() {
        return true;
    }

    private static boolean error3() {
        return true;
    }

    private static boolean error4() {
        Node node = controller.findNode("0");
        if(node == null) {
            Errors.groundError();
            return false;
        }
        //TODO
        return true;
    }

    private static boolean error5() {
        for (Node node : controller.getNodes()) {
            if (node.getElements().size() + node.getSources().size() < 2) {
                Errors.errors(-5, "Node " + node.getName() + " is floating.");
                return false;
            }
        }
        Node node = controller.findNode("0");
        if (node == null) {
            return error4();
        } else {
            node.setGroundMatch(true);
            getGroundMatches(node);
            for (Node controllerNode : controller.getNodes()) {
                if (!controllerNode.isGroundMatch()) {
                    Errors.errors(-5, "Node " + controllerNode.getName() + " is floating.");
                    return false;
                }
            }
        }
        return true;
    }

    private static void getGroundMatches(Node node) {
        for (Node neighborNode : node.getNeighborNodes()) {
            if (!neighborNode.isGroundMatch()) {
                neighborNode.setGroundMatch(true);
                getGroundMatches(neighborNode);
            }
        }
    }
}
