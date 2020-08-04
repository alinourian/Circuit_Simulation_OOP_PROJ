package model;

import java.util.ArrayList;

public class Branch {
    protected final String name;
    protected Node nodeP;
    protected Node nodeN;
    private Branch superiorBranch;
    private ArrayList<Branch> subBranches = new ArrayList<>();
    private String type = new String();
    private int width;
    private int height;
    private int isVisited;

    public Branch(String name, Node nodeP, Node nodeN) {
        this.name = name;
        this.nodeP = nodeP;
        this.nodeN = nodeN;
        this.superiorBranch = null;

    }


    public void setWidth(int width) { this.width = width; }

    public void setHeight(int height) { this.height = height; }

    public void setSuperiorBranch(Branch superiorBranch) { this.superiorBranch = superiorBranch; }

    public void setSubBranches(ArrayList<Branch> subBranches) { this.subBranches = subBranches; }

    public void setTheTypeParallel() { this.type = "Parallel"; }

    public void setTheTypeSeries() { this.type = "Series"; }

    public void setVisited() { this.isVisited = 1; }

    public void setNotVisited() {this.isVisited = 0; }

    public boolean getIsVisited() { return isVisited == 1; }

    public Node getNodeP() { return nodeP; }

    public Node getNodeN() { return nodeN; }

    public ArrayList<Branch> getSubBranches() { return subBranches; }

    public Branch getSuperiorBranch() { return superiorBranch; }

    public String getName() { return name; }

    public int getWidth() { return width; }

    public int getHeight() { return height; }
}
