package model;

public class Branch {
    protected final String name;
    protected Node nodeP;
    protected Node nodeN;
    private Branch subBranches;
    private int width;
    private int height;

    public Branch(String name, Node nodeP, Node nodeN) {
        this.name = name;
        this.nodeP = nodeP;
        this.nodeN = nodeN;
        this.subBranches = null;
        this.height = 1;
        this.width = 1;
    }


    public void setSubBranches(Branch subBranches) { this.subBranches = subBranches; }

    public void setWidth(int width) { this.width = width; }

    public void setHeight(int height) { this.height = height; }

    public String getName() {
        return name;
    }

    public Branch getSubBranches() { return subBranches; }

    public int getWidth() { return width; }

    public int getHeight() { return height; }
}
