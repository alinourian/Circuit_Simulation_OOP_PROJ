package model;

import java.util.ArrayList;

public class Branch {
    protected final String name;
    protected Node nodeP;
    protected Node nodeN;
    private Branch superiorBranch;
    private ArrayList<Branch> subBranches = new ArrayList<>();
    private int width;
    private int height;

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

    public ArrayList<Branch> getSubBranches() { return subBranches; }

    public Branch getSuperiorBranch() { return superiorBranch; }

    public String getName() { return name; }

    public int getWidth() { return width; }

    public int getHeight() { return height; }
}
