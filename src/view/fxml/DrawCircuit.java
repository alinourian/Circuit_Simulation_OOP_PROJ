package view.fxml;

import controller.InputController;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import model.Branch;
import model.Element;
import model.Node;
import model.Source;

import java.util.ArrayList;

public abstract class DrawCircuit {
    private static double drawingUnitLength;
    private static final Pane circuitPane = new Pane();
    private static final InputController controller = InputController.getInstance();
    private static ArrayList<Branch> allBranchesTemp = new ArrayList<>();
    private static ArrayList<Branch>    newSeriesBranches   = new ArrayList<>();
    private static ArrayList<Branch>    newTempBranch   = new ArrayList<>();
    private static Node newTempBranchStartNode;
    private static Node newTempBranchEndNode;

    private static final Image resistor = new Image("view/img/element/Resistor.png");
    private static final Image capacitor = new Image("view/img/element/Capacitor.png");
    private static final Image inductor = new Image("view/img/element/Inductor.png");
    private static final Image diode = new Image("view/img/element/Diode.png");
    private static final Image gnd = new Image("view/img/element/Ground.png");
    private static final Image vSource = new Image("view/img/element/VSource.png");
    private static final Image cSource = new Image("view/img/element/CSource.png");
    private static final Image acSource = new Image("view/img/element/AcSource.png");
    private static final Image controlledVSource = new Image("view/img/element/ControlledVSource.png");
    private static final Image controlledCSource = new Image("view/img/element/ControlledCSource.png");
    private static final Image wire = new Image("view/img/element/Wire.png");

    public static Pane drawCircuit() {
        drawingUnitLength = 80;

        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 5; j++) {
                circuitPane.getChildren().add(new Circle(getXY(i), getXY(j), 1));
            }
        }


        setTheFinalSuperiorBranch();
        // TODO  set branches height and width
        // TODO find max height and width
        // TODO set drawingUnitLength
        // Todo


        putGround();
        putImage(resistor, 9, 3);
        putImage(capacitor, 10, 4);
        putImage(cSource, 11, 5);
        putImage(inductor, 12, 11);
        putImage(controlledCSource, 12, 6);
        matchNodes(9, 10, 11);
        matchNodes(3, 4, 5);
        matchNodes(19, 25, 26, 27, 28, 29);
        putImage(diode, 6, 5);
        putImage(acSource, 9, 8);
        putImage(controlledVSource, 7, 8);
        putImage(wire, 7, 1);
        putImage(wire, 1, 2);
        putImage(vSource, 2, 3);
        putImage(inductor, 29, 30);
        putImage(resistor, 9.5, 3.5);
        return circuitPane;
    }


    private static void setTheFinalSuperiorBranch()
    {


        for (Source source : controller.getSources()) {
            allBranchesTemp.add(source);
        }

        for (Element element : controller.getElements()) {
            allBranchesTemp.add(element);
        }


        while (allBranchesTemp.size() != 1)
        {
            incorporateParallelBranchesToNewBranch();




        }



        controller.setFinalSuperiorBranch(allBranchesTemp.get(0));

    }

    private static void incorporateParallelBranchesToNewBranch()
    {
        controller.setAllNodesNotVisited();
        processParallelIncorporationForEachNode(controller.getGround());

    }

    private static void processParallelIncorporationForEachNode(Node node) {
        node.setVisited();

        for (Node neighborNode : node.getNeighborNodes())
        {
            if (!neighborNode.getIsVisited())
            {
                ArrayList<Branch> branches;
                branches = getBranchesBetweenTwoNeighborNodes(node, neighborNode);


                if (branches.size() > 1) {
                    StringBuilder newBranchName = new StringBuilder();

                    for (Branch branch : branches) {
                        allBranchesTemp.remove(branch);
                        newBranchName.append(branch.getName() + " ");
                    }

                    Branch newBranch = new Branch(newBranchName.toString(), neighborNode, node);
                    newBranch.setTheTypeParallel();

                    for (Branch branch : branches) {
                        branch.setSuperiorBranch(newBranch);
                        newBranch.getSubBranches().add(branch);
                    }
                    allBranchesTemp.add(newBranch);

                }
            }
        }

        for (Node neighborNode : node.getNeighborNodes())
        {
            processParallelIncorporationForEachNode(neighborNode);
        }

    }


    private static ArrayList<Branch> getBranchesBetweenTwoNeighborNodes(Node node1, Node node2)
    {
        ArrayList<Branch> branches = new ArrayList<>();

        for (Branch branch : allBranchesTemp) {
            if ( ( branch.getNodeN().equals(node1) && branch.getNodeP().equals(node2) ) ||
                    ( branch.getNodeP().equals(node1) && branch.getNodeN().equals(node2) ) )
            {
                branches.add(branch);
            }
        }

        return branches;
    }


    private static void incorporateSeriesBranchesToNewBranch()
    {
        newSeriesBranches.clear();

        controller.setAllNodesNotVisited();

        setBranchesNotVisited(allBranchesTemp);

        processSeriesIncorporationForEachNode(controller.getGround());

    }


    private static void processSeriesIncorporationForEachNode(Node node)
    {
        newTempBranchStartNode = node;

        for (Branch branch : getNeighborBranchesOfThisNode(node)) {
            newTempBranch.clear();
            findConnectedSeriesBranches(node,branch);
        }

    }

    private static void findConnectedSeriesBranches(Node node,Branch branch)
    {
        if (!branch.getIsVisited())
        {

        }
    }


    private static ArrayList<Branch>    getNeighborBranchesOfThisNode(Node node)
    {
        ArrayList<Branch>   branches    = new ArrayList<>();

        for (Branch branch : allBranchesTemp)
        {
         if (branch.getNodeN().equals(node) || branch.getNodeP().equals(node))
         {
             branches.add(branch);
         }
        }
        return branches;
    }


    private static ArrayList<Node>  getShownNodesOfAllBranchesTem()
    {
        ArrayList<Node> nodes   = new ArrayList<>();

        for (Branch branch : allBranchesTemp)
        {
            if ( !nodes.contains(branch.getNodeN()) )
            {
                nodes.add(branch.getNodeN());
            }

            if ( !nodes.contains(branch.getNodeP()) )
            {
                nodes.add(branch.getNodeP());
            }
        }

        return nodes;
    }

    private static void setBranchesNotVisited(ArrayList<Branch> branches)
    {
        for (Branch branch : branches) {
            branch.setNotVisited();
        }
    }

    private static void setParallelElement() {
        InputController controller = new InputController();
        int max = 0;
        for (Node node : controller.getNodes()) {
            max = Math.max(max, node.getElements().size() + node.getSources().size());
        }
        Node middleNode;
        for (Node node : controller.getNodes()) {
            if (max == node.getElements().size() + node.getSources().size()) {
                middleNode = node;
            }
        }

    }

    private static void matchNodes(double... doubles) {
        double[] nodes = doubles.clone();
        for (int i = 1; i < nodes.length; i++) {
            putImage(wire, nodes[i], nodes[i - 1]);
        }
    }

    private static PaneNode convertNodeNumToPaneNode(double num) {
        double x = num % 6 != 0 ? num % 6 : 6;
        double y = 6 - Math.ceil(num / 6 );
        return getNode(x, y);
    }

    private static double getXY(double XY) {
        return XY * drawingUnitLength;
    }

    private static PaneNode getNode(double x, double y) {
        return new PaneNode(getXY(x), getXY(y));
    }

    private static PaneNode getLayoutNode(PaneNode middlePoint) {
        return new PaneNode(middlePoint.nodeX - drawingUnitLength / 2, middlePoint.nodeY - drawingUnitLength / 2);
    }

    private static void putGround() {
        ImageView imageView = new ImageView(gnd);
        imageView.setFitWidth(drawingUnitLength);
        imageView.setFitHeight(drawingUnitLength);
        imageView.setLayoutX(getXY(3));
        imageView.setLayoutY(getXY(5));
        circuitPane.getChildren().add(imageView);
    }

    private static void putImage(Image image, double numNode1, double numNode2) {
        PaneNode node1 = convertNodeNumToPaneNode(numNode1);
        PaneNode node2 = convertNodeNumToPaneNode(numNode2);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(drawingUnitLength);
        imageView.setFitHeight(drawingUnitLength);
        if (node1.nodeX == node2.nodeX && Math.abs(node1.nodeY - node2.nodeY) == getXY(1)) {
            if (node1.nodeY > node2.nodeY) {
                imageView.setRotate(180);
            }
            PaneNode middlePoint = new PaneNode(node1.nodeX, (node1.nodeY + node2.nodeY) / 2);
            PaneNode node = getLayoutNode(middlePoint);
            imageView.setLayoutX(node.nodeX);
            imageView.setLayoutY(node.nodeY);
            circuitPane.getChildren().add(imageView);
        } else if (node1.nodeY == node2.nodeY && Math.abs(node1.nodeX - node2.nodeX) == getXY(1)) {
            if (node1.nodeX < node2.nodeX) {
                imageView.setRotate(-90);
            } else {
                imageView.setRotate(90);
            }
            PaneNode middlePoint = new PaneNode((node1.nodeX + node2.nodeX) / 2, node1.nodeY);
            PaneNode node = getLayoutNode(middlePoint);
            imageView.setLayoutX(node.nodeX);
            imageView.setLayoutY(node.nodeY);
            circuitPane.getChildren().add(imageView);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Not Possible!");
            alert.show();
        }
    }


}

class PaneNode {
    double nodeX;
    double nodeY;
    int nodeNumber;

    public PaneNode(double nodeX, double nodeY) {
        this.nodeX = nodeX;
        this.nodeY = nodeY;
    }

    public void setNodeNumber(int nodeNumber) {
        this.nodeNumber = nodeNumber;
    }

    @Override
    public String toString() {
        return "PaneNode{" +
                "nodeX=" + nodeX +
                ", nodeY=" + nodeY +
                '}';
    }
}