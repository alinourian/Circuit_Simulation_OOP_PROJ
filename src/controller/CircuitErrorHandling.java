package controller;

import model.Node;
import model.Source;
import model.VoltageSource;
import view.Errors;

public class CircuitErrorHandling {

    private static final InputController controller = InputController.getInstance();

    public static boolean errors() {
        return error4() && error5();
    }

    private static boolean error4() {
        Node node = controller.findNode("0");
        if(node == null) {
            Errors.groundError();
            return false;
        } else {
            return error4HelpingMethod(node, node);
        }
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

    private static boolean error4HelpingMethod(Node node, Node nodeOff) {
        for (Source source : node.getSources()) {
            if (source instanceof VoltageSource) {
                Node node0 = source.getNodeP().getName().equals(node.getName()) ? source.getNodeN() : source.getNodeP();
                if (node0 != nodeOff) {
                    if (node0.getName().equals("0")) {
                        Errors.errors(-4, "voltages conflicted!");
                        return false;
                    } else {
                        return CircuitErrorHandling.error4HelpingMethod(node0, node);
                    }
                }
            }
        }
        return true;
    }
}
