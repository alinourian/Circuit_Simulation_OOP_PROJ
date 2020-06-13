package model;

public class Source {
    protected final String name;
    protected Node nodeP;
    protected Node nodeN;

    public Source(String name, Node nodeIn, Node nodeOut) {
        this.name = name;
        this.nodeP = nodeIn;
        this.nodeN = nodeOut;
    }

    public String getName() {
        return name;
    }

    public double getValue(double time) {
        return 0;
    }

    public Node getNodeP() {
        return nodeP;
    }

    public Node getNodeN() {
        return nodeN;
    }

    public String getType() {
        return "Source";
    }
}
