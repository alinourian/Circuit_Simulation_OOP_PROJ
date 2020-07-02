package model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Union {
    private String name;
    private ArrayList<Node> nodes;
    private Node fatherOfUnion;

    public Union(String name, Node fatherOfUnion) {
        this.name = new String();
        this.fatherOfUnion = fatherOfUnion;
        addNodeToUnion(fatherOfUnion);
    }


    public void addNodeToUnion(Node node){
        nodes.add(node);
        updateName(node.getName());

    }

    public void updateName(String nodeName) {

        name = name + " " + nodeName;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
        for (Node node : nodes) {
            updateName(node.getName());
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
