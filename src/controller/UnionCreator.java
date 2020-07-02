package controller;

import model.Node;
import model.VoltageSource;

public class UnionCreator {

    private final InputController controller = InputController.getInstance();

    public boolean run() {

        Node groundNode = null;
        for (Node node : controller.getNodes()) {
            if (node.getName().equals("0")) {
                groundNode = node;
            }
        }

        if (groundNode == null) {
            System.err.println("ground not found!");
            return false;
        }

        processNodesParentSetting(groundNode);


        return true;
    }

    private void processNodesParentSetting(Node groundNode) {

        controller.setAllNodesNotVisited();
        searchForDependentNode(groundNode);

    }

    private void searchForDependentNode(Node inputNode) {


        if (inputNode.getParentNode() == null)
            inputNode.setParentNode(inputNode);

        for (Node neighborNode : inputNode.getNeighborNodes()) {

            if (neighborNode.getIsVisited() == 0) {

                for (VoltageSource voltageSource : controller.getVoltageSources()) {

                    if ((voltageSource.getNodeP() == inputNode && voltageSource.getNodeN() == neighborNode) ||
                            (voltageSource.getNodeN() == inputNode && voltageSource.getNodeP() == neighborNode)) {
                        neighborNode.setParentNode(inputNode);
                        break;
                    }
                }

                if (neighborNode.getParentNode() == null)
                    neighborNode.setParentNode(neighborNode);

            }
        }



        inputNode.setVisited();
    }

}
