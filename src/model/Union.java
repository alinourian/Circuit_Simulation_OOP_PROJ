package model;

import view.Errors;

import java.util.ArrayList;

public class Union {
    private String name;
    private ArrayList<Node> nodes = new ArrayList<>();
    private Node fatherOfUnion;
    private String type = new String();

    public Union(Node fatherOfUnion) {
        this.name = fatherOfUnion.getName();
        this.fatherOfUnion = fatherOfUnion;
        addNodeToUnion(fatherOfUnion);

        this.fatherOfUnion.setIncludingUnion(this);
    }

    public Union(Node fatherOfUnion,ArrayList<Node> nodes) {
        this.name = fatherOfUnion.getName();
        this.fatherOfUnion = fatherOfUnion;
        addNodeToUnion(fatherOfUnion);
        addNodesToUnion(nodes);

        for (Node node : this.nodes) {
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

    public boolean updateNodesVoltages() {
        for (Node node : getNodes()) {
            node.setUpdateUnionCounter(0);
        }
        return processNodesVoltagesUpdate(fatherOfUnion);
    }

    public boolean processNodesVoltagesUpdate(Node node) {
        for (Node neighborNode : node.getNeighborNodes()) {
            if (node.getIncludingUnion().getNodes().contains(neighborNode)) {
                if (neighborNode.getParentNode().equals(node)) {
                    for (Source source : node.getSources()) {
                        if (source instanceof VoltageSource) {
                            VoltageSource voltageSource = (VoltageSource) source;
                            if ( ( voltageSource.getNodeP().equals(node) && voltageSource.getNodeN().equals(neighborNode) ) ||
                                    ( voltageSource.getNodeN().equals(node) && voltageSource.getNodeP().equals(neighborNode)) ) {
                                double temp = neighborNode.getVoltage();
                                if (neighborNode.getUpdateUnionCounter() >= 1) {
                                    neighborNode.setVoltage(node.getVoltage() + voltageSource.getVoltage(neighborNode));
                                    if (temp != neighborNode.getVoltage()) {
                                        Errors.errors(-3, "Voltages conflicted!");
                                        return false;
                                    } else {
                                        neighborNode.setUpdateUnionCounter(neighborNode.getUpdateUnionCounter() + 1);
                                        neighborNode.setVoltage(node.getVoltage() + voltageSource.getVoltage(neighborNode));
                                    }
                                } else {
                                    neighborNode.setUpdateUnionCounter(neighborNode.getUpdateUnionCounter() + 1);
                                    neighborNode.setVoltage(node.getVoltage() + voltageSource.getVoltage(neighborNode));
                                }
                            }
                        }
                    }
                    return processNodesVoltagesUpdate(neighborNode);
                }
            }
        }
        return true;
    }

    public double getTotalCurrent() {
        double totalCurrent = 0 ;

        if (this.getType().equals("SingleNode")) {
            totalCurrent += this.getFatherOfUnion().getTotalCurrent();
            return totalCurrent;
        }
        for (Node node : this.getNodes()) {

            for (Node neighborNode : node.getNeighborNodes()) {
                if (!this.getNodes().contains(neighborNode)) {
                    for (Element element : node.getElements()) {
                        if ((element.getNodeP().equals(node) && element.getNodeN().equals(neighborNode)) ||
                                (element.getNodeN().equals(node) && element.getNodeP().equals(neighborNode))) {
                            totalCurrent += element.getCurrent(node);
                        }
                    }
                    for (Source source : node.getSources()) {
                        if (source instanceof CurrentSource) {
                            if ((source.getNodeP().equals(node) && source.getNodeN().equals(neighborNode)) ||
                                    (source.getNodeN().equals(node) && source.getNodeP().equals(neighborNode))) {
                                CurrentSource currentSource = (CurrentSource) source;
                                totalCurrent += currentSource.getCurrent(node);
                            }
                        }
                    }

                }
            }
        }
        return totalCurrent;
    }

    public void setFatherOfUnion(Node fatherOfUnion) {
        this.fatherOfUnion = fatherOfUnion;
    }

    public void setType(String type) { this.type = type; }

    public String getType() { return type; }

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
