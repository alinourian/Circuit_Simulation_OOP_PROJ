package controller;

import model.Node;
import model.Union;
import model.VoltageSource;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UnionCreator {

    private final InputController controller = InputController.getInstance();

    public boolean run()
    {

        Node groundNode = null;
        for (Node node : controller.getNodes())
            if (node.getName().equals("0"))
            {
                groundNode = node;
            }

        if (groundNode == null) {
            System.err.println("ground not found!");
            return false;
        }

        processNodesParentSetting(groundNode);

        controller.setAllNodesNotVisited();

        ArrayList<Node> tempNodes = new ArrayList<>(controller.getNodes());

        processUnionsCreation(tempNodes);



        // PRINTING PARENT OF EACH NODE
        /*for (Node node : controller.getNodes()) {
            System.out.println("parent of node "+ node.getName()+" is:" + node.getParentNode().getName());
        }*/

        // PRINTING UNIONS
        /*System.out.println("\n---------------");
        for (Union union : controller.getUnions()) {
            System.out.println("Union father is: " + union.getFatherOfUnion().getName());
            System.out.println("Nodes are: ");
            for (Node node : union.getNodes()) {
                System.out.println(node.getName());
            }
            System.out.println("---------------");
        }*/

        return true;
    }

    private void processNodesParentSetting(Node groundNode) {

        controller.setAllNodesNotVisited();
        searchForDependentNode(groundNode);

    }

    private void searchForDependentNode(Node inputNode) {

        if (inputNode.getParentNode() == null)
        {
            inputNode.setParentNode(inputNode);

        }
        ArrayList<Node> unVisitedNeighborNodes = new ArrayList<>();

        for (Node neighborNode : inputNode.getNeighborNodes())
        {
            if ( !neighborNode.getIsVisited() )
            {
                unVisitedNeighborNodes.add(neighborNode);
                for (VoltageSource voltageSource : controller.getVoltageSources())
                {

                    if ((voltageSource.getNodeP() == inputNode && voltageSource.getNodeN() == neighborNode) ||
                            (voltageSource.getNodeN() == inputNode && voltageSource.getNodeP() == neighborNode))
                    {
                        if (neighborNode.getParentNode() == null)
                        {
                            neighborNode.setParentNode(inputNode);
                        }
                        else
                        {
                            inputNode.setParentNode(neighborNode);
                        }


                        break;
                    }
                }

            }
        }
        inputNode.setVisited();

        for (Node unVisitedNeighborNode : unVisitedNeighborNodes) {
            searchForDependentNode(unVisitedNeighborNode);
        }
    }

    private void processUnionsCreation(ArrayList<Node> nodes) {

        for (Node node : nodes)
        {
            if (node.getParentNode() == node && !isThisNodeParentOfAnyOtherNode(node))
            {
                createSingleNodeUnion(node);
            }
        }

        for (Union union : controller.getUnions()) {
            nodes.remove(union.getFatherOfUnion());
        }

        ArrayList<Node> unionsFathers = new ArrayList<>();

        for (Node node : nodes) {
            if (node.getParentNode() == node){
                unionsFathers.add(node);

            }
        }

        for (Node unionsFather : unionsFathers) {
            nodes.remove(unionsFather);
        }

        for (Node unionsFather : unionsFathers) {
            ArrayList<Node> foundNodes = new ArrayList<>();
            searchForMatesInUnion(unionsFather,nodes,foundNodes);
            createMultiNodeUnion(unionsFather,foundNodes);
        }
    }

    private void searchForMatesInUnion(Node main,ArrayList<Node> nodes,ArrayList<Node> foundNodes){
        for (Node node : nodes) {
            if (node.getParentNode() == main)
            {
                foundNodes.add(node);
                ArrayList<Node> otherNodes = new ArrayList<>(nodes);
                otherNodes.remove(node);
                searchForMatesInUnion(node,otherNodes,foundNodes);
            }
        }
    }

    private void createSingleNodeUnion(Node node) {
        controller.getUnions().add(new Union(node));
    }

    private void createMultiNodeUnion(Node fatherNode,ArrayList<Node> nodes) {
        controller.getUnions().add(new Union(fatherNode,nodes));
    }

    private boolean isThisNodeParentOfAnyOtherNode(Node node) {
        ArrayList<Node> tempNodes = new ArrayList<>(controller.getNodes());
        tempNodes.remove(node);
        for (Node tempNode : tempNodes)
        {
            if (tempNode.getParentNode() == node)
                return true;
        }
        return false;
    }
}
