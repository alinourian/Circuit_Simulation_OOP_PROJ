package model;

import java.util.ArrayList;

public class Union {
    private String name;
    private ArrayList<Node> nodes = new ArrayList<>();
    private Node fatherOfUnion;

    public Union(Node fatherOfUnion) {
        this.name = fatherOfUnion.getName();
        this.fatherOfUnion = fatherOfUnion;
        addNodeToUnion(fatherOfUnion);
        fatherOfUnion.setIncludingUnion(this);
    }

    public Union(Node fatherOfUnion,ArrayList<Node> nodes) {
        this.name = fatherOfUnion.getName();
        this.fatherOfUnion = fatherOfUnion;
        addNodeToUnion(fatherOfUnion);
        addNodesToUnion(nodes);
        for (Node node : nodes) {
            node.setIncludingUnion(this);
        }

    }

    public void addNodeToUnion(Node node){
        this.nodes.add(node);
    }

    public void addNodesToUnion(ArrayList<Node> inputNodes) {
        for (Node inputNode : inputNodes) {
            if (this.nodes.contains(inputNode))
                continue;
            else
                this.nodes.add(inputNode);
        }
    }

    public void setFatherOfUnion(Node fatherOfUnion) {
        this.fatherOfUnion = fatherOfUnion;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public Node getFatherOfUnion() {
        return fatherOfUnion;
    }
}
