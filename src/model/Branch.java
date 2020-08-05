package model;

import java.util.ArrayList;

public class Branch {
    protected final String name;
    protected Node nodeP;
    protected Node nodeN;
    private Branch superiorBranch;
    private ArrayList<Branch> subBranches = new ArrayList<>();
    private String structureType = new String();
    private int width = 0;
    private int height = 0;
    private int isVisited;

    public Branch(String name, Node nodeP, Node nodeN) {
        this.name = name;
        this.nodeP = nodeP;
        this.nodeN = nodeN;
        this.superiorBranch = null;

    }

    public Node getAnotherNodeOfBranch(Node node)
    {
        if (this.nodeP.equals(node))
        {
            return this.nodeN;
        }
        else if (this.nodeN.equals(node))
        {
            return this.nodeP;
        }
        else
        {
            return null;
        }
    }

    public boolean IsItParallel()
    {
        return this.getStructureType().equals("Parallel");
    }

    public void setWidth(int width) { this.width = width; }

    public void setHeight(int height) { this.height = height; }

    public void setSuperiorBranch(Branch superiorBranch) { this.superiorBranch = superiorBranch; }

    public void setSubBranches(ArrayList<Branch> subBranches) { this.subBranches = subBranches; }

    public void setTheTypeParallel() { this.structureType = "Parallel"; }

    public void setTheTypeSeries() { this.structureType = "Series"; }

    public void setVisited() { this.isVisited = 1; }

    public void setNotVisited() {this.isVisited = 0; }

    public String getStructureType() { return structureType; }

    public boolean getIsVisited() { return isVisited == 1; }

    public Node getNodeP() { return nodeP; }

    public Node getNodeN() { return nodeN; }

    public ArrayList<Branch> getSubBranches() { return subBranches; }

    public Branch getSuperiorBranch() { return superiorBranch; }

    public String getName() { return name; }

    public int getWidth() { return width; }

    public int getHeight() { return height; }
}
