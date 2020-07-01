package model;

public class Branch {
    protected final String name;
    protected Node nodeP;
    protected Node nodeN;

    public Branch(String name, Node nodeP, Node nodeN) {
        this.name = name;
        this.nodeP = nodeP;
        this.nodeN = nodeN;
    }
}
