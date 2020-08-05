package view.fxml;

import controller.InputController;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import model.*;

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
    private static int maxHorizontalNodes;
    private static int maxVerticalNodes;


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

        circuitPane.getChildren().clear();

        drawingUnitLength = 80;


        setTheFinalSuperiorBranch();

        setBranchesHeightAndWidth();

        setMaxHorizontalAndVerticalNodes();


        System.out.println("max H is: "+maxHorizontalNodes);
        System.out.println("max V is: "+maxVerticalNodes);
        drawTheNodes();

        drawTheFuckingCircuitDiagram();


        ArrayList<Branch>   temp = new ArrayList<>();
        temp.add(controller.getFinalSuperiorBranch());
        // printAllBranchesAndSubbranches(temp);
        putGround();

        return circuitPane;
    }


    private static void drawTheFuckingCircuitDiagram()
    {
        PaneNode first = new PaneNode( (maxHorizontalNodes-1)/2*drawingUnitLength + drawingUnitLength
                                                , drawingUnitLength);

        PaneNode second = new PaneNode( (maxHorizontalNodes-1)/2*drawingUnitLength + drawingUnitLength
                                            , (maxVerticalNodes-1)*drawingUnitLength + 80);

        processDrawingTheBranches(controller.getFinalSuperiorBranch(), first , second);


    }

    private static void processDrawingTheBranches(Branch branch , PaneNode first , PaneNode second )
    {
        if (branch.getHeight() == 1 && branch.getWidth() == 1)
        {
            drawThisBranch(branch,first,second);
        }
        else if (branch.IsItParallel())
        {
            double firstPaneNodeWire_X = first.nodeX - (branch.getWidth()-1)/2*drawingUnitLength
                    + (branch.getSubBranches().get(0).getWidth()-1)/2*drawingUnitLength;

            double secondPaneNodeWire_X = 0;

            double branches_X = first.nodeX - (branch.getWidth()-1)/2*drawingUnitLength;

            for (Branch subBranch : branch.getSubBranches())
            {
                branches_X += (subBranch.getWidth()-1)/2*drawingUnitLength;

                PaneNode newFirst = new PaneNode(branches_X ,first.nodeY);
                PaneNode newSecond = new PaneNode(branches_X , second.nodeY);

                processDrawingTheBranches(subBranch,newFirst,newSecond);

                secondPaneNodeWire_X = branches_X;

                branches_X += (subBranch.getWidth()-1)/2*drawingUnitLength + drawingUnitLength;
            }


            PaneNode firstWireFirstPaneNode = new PaneNode( firstPaneNodeWire_X ,first.nodeY );
            PaneNode firstWireSecondPaneNode = new PaneNode( secondPaneNodeWire_X, first.nodeY );


            PaneNode secondWireFirstPaneNode = new PaneNode( firstPaneNodeWire_X ,second.nodeY );
            PaneNode secondWireSecondPaneNode = new PaneNode( secondPaneNodeWire_X , second.nodeY );


            System.out.println("heree");
            System.out.println("firstWireFirstPaneNode: "+firstWireFirstPaneNode);
            System.out.println("firstWireSecondPaneNode: "+firstWireSecondPaneNode);
            System.out.println("secondWireFirstPaneNode: "+secondWireFirstPaneNode);
            System.out.println("secondWireSecondPaneNode: "+secondWireSecondPaneNode);
            putWire( firstWireFirstPaneNode , firstWireSecondPaneNode );
            putWire( secondWireFirstPaneNode , secondWireSecondPaneNode );
        }
        else
        {

        }


    }


    private static void drawThisBranch(Branch branch , PaneNode first , PaneNode second)
    {

        if (branch instanceof Element)
        {
            if (branch instanceof Capacitor)
            {
                putImage(capacitor,first,second);
            }
            if (branch instanceof Diode)
            {
                putImage(diode,first,second);
            }
            if (branch instanceof Inductor)
            {
                putImage(inductor,first,second);
            }
            if (branch instanceof Resistor)
            {
                putImage(resistor,first,second);
            }

        }
        else if(branch instanceof Source)
        {
            if (branch instanceof VoltageSource)
            {
                if (branch instanceof VoltageControlledVoltageSource)
                {

                    putImage(controlledVSource,first,second);
                }
                else if(branch instanceof CurrentControlledVoltageSource)
                {

                    putImage(controlledVSource,first,second);
                }
                else
                {
                    if (((VoltageSource) branch).getPhase() == 0)
                    {
                        // DC VOLTAGE SOURCE

                        putImage(vSource,first,second);
                    }
                    else
                    {
                        // AC VOLTAGE SOURCE

                        putImage(acSource,first,second);
                    }

                }
            }
            else if(branch instanceof CurrentSource)
            {
                if (branch instanceof CurrentControlledCurrentSource)
                {

                    putImage(controlledCSource,first,second);
                }
                else if (branch instanceof VoltageControlledCurrentSource)
                {

                    putImage(controlledCSource,first,second);
                }
                else
                {
                    if (((CurrentSource) branch).getPhase() == 0)
                    {
                        // DC CURRENT SOURCE

                        putImage(cSource,first,second);
                    }
                    else
                    {
                        // AC CURRENT SOURCE

                        putImage(acSource,first,second);
                    }
                }
            }
        }


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

            incorporateSeriesBranchesToNewBranch();

        }


        // printAllBranchesAndSubbranches(allBranchesTemp);

        controller.setFinalSuperiorBranch(allBranchesTemp.get(0));

    }

    private static void printAllBranchesAndSubbranches(ArrayList<Branch>    branches)
    {
        for (Branch branch : branches) {
            System.out.println("branch name is :"+branch.getName());
            System.out.println("Nope p is: "+branch.getNodeP().getName());
            System.out.println("Nope n is: "+branch.getNodeN().getName());
            System.out.println("height: "+branch.getHeight());
            System.out.println("width: "+branch.getWidth());
            System.out.println("");
        }

        for (Branch branch : branches) {
            if (branch.getSubBranches().size() != 0)
            {
                printAllBranchesAndSubbranches(branch.getSubBranches());
            }
        }
    }
    private static ArrayList<Branch> getAllBranchesAndSubbranches()
    {
        ArrayList<Branch> outputBranches = new ArrayList<>();
        ArrayList<Branch> processBranches = new ArrayList<>();
        processBranches.add(controller.getFinalSuperiorBranch());
        processGettingAllBranchesAndSubbranches(processBranches,outputBranches);
        return outputBranches;


    }
    private static void processGettingAllBranchesAndSubbranches(ArrayList<Branch> branches,ArrayList<Branch> outputBranches)
    {
        for (Branch branch : branches) {
            outputBranches.add(branch);
        }

        for (Branch branch : branches) {
            if (branch.getSubBranches().size() !=0 )
            {
                processGettingAllBranchesAndSubbranches(branch.getSubBranches(),outputBranches);
            }
        }
    }

    private static void print()
    {
        for (Branch branch : allBranchesTemp)
        {
            System.out.println("branch name is :"+branch.getName());
            System.out.println("Nope p is: "+branch.getNodeP().getName());
            System.out.println("Nope n is: "+branch.getNodeN().getName());
        }
        System.out.println("");

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
            if (!neighborNode.getIsVisited())
            {
                processParallelIncorporationForEachNode(neighborNode);
            }

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

        for (Branch branch : newSeriesBranches)
        {
            for (Branch subBranch : branch.getSubBranches())
            {
                allBranchesTemp.remove(subBranch);
            }
            allBranchesTemp.add(branch);
        }
    }


    private static void processSeriesIncorporationForEachNode(Node node)
    {
        newTempBranchStartNode = node;

        for (Branch branch : getNeighborBranchesOfThisNode(node)) {
            newTempBranch.clear();
            findConnectedSeriesBranches(node,branch);
        }

        int counter = 0;

        for (Branch branch : allBranchesTemp)
        {
            if (!branch.getIsVisited())
            {
                counter++;
            }
        }

        if ( counter != 0 )
        {

            for (Branch branch : getNeighborBranchesOfThisNode(node))
            {
                processSeriesIncorporationForEachNode(branch.getAnotherNodeOfBranch(node));
            }

        }


    }

    private static void findConnectedSeriesBranches(Node node,Branch branch)
    {
        if (!branch.getIsVisited())
        {
            branch.setVisited();

            if ( ( getNeighborBranchesOfThisNode( branch.getAnotherNodeOfBranch(node) ).size() == 2 ) &&
                    ( !newTempBranchStartNode.equals( branch.getAnotherNodeOfBranch(node) ) ) )
            {
                /*System.out.println("input node: "+node.getName());
                System.out.println("input branch: "+branch.getName());*/

                newTempBranch.add(branch);
                Branch connectedBranch = null;

                for (Branch branch1 : getNeighborBranchesOfThisNode(branch.getAnotherNodeOfBranch(node)))
                {
                    if (!branch.equals(branch1))
                    {
                        connectedBranch = branch1;
                    }
                }

                findConnectedSeriesBranches( branch.getAnotherNodeOfBranch(node) , connectedBranch );
            }
            else
            {
                /*System.out.println("input node: "+node.getName());
                System.out.println("input branch: "+branch.getName());*/


                if ( !newTempBranchStartNode.equals( branch.getAnotherNodeOfBranch(node) ) )
                {
                    newTempBranch.add(branch);
                }

                if (newTempBranch.size() >= 2)
                {
                    if ( !newTempBranchStartNode.equals( branch.getAnotherNodeOfBranch(node) ) )
                    {
                        newTempBranchEndNode = branch.getAnotherNodeOfBranch(node);
                    }
                    else
                    {
                        newTempBranchEndNode = node;
                    }

                    /*System.out.println("new Temp Branch Start Node is :"+newTempBranchStartNode.getName());
                    System.out.println("new Temp Branch End Node is :"+newTempBranchEndNode.getName());*/

                    StringBuilder newBranchName = new StringBuilder();

                    for (Branch tempBranch : newTempBranch)
                    {
                        newBranchName.append(tempBranch.getName()+" ");
                    }

                    Branch newBranch = new Branch(newBranchName.toString(),newTempBranchStartNode,newTempBranchEndNode);
                    newBranch.setTheTypeSeries();

                    for (Branch tempBranch : newTempBranch) {
                        newBranch.getSubBranches().add(tempBranch);
                        tempBranch.setSuperiorBranch(newBranch);
                    }

                    newSeriesBranches.add(newBranch);

                }

            }


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


    private static void processSettingUpTheBranchesHeightAndWidth(ArrayList<Branch> branches)
    {

        for (Branch branch : branches)
        {
            int visitedSubbranches = 0 ;
            for (Branch subBranch : branch.getSubBranches())
            {
                if (subBranch.getIsVisited())
                {
                    visitedSubbranches++;
                }
            }

            if ( ( branch.getSubBranches().size() == 0 || branch.getSubBranches().size() == visitedSubbranches ) &&
                    ( !branch.getIsVisited() )  )
            {
                if (!branch.equals(controller.getFinalSuperiorBranch()))
                {
                    Branch superiorBranch = branch.getSuperiorBranch();
                    System.out.println("----------------------------------");

                    if (superiorBranch.IsItParallel())
                    {
                        System.out.println("");
                        System.out.println("branch: "+branch.getName());
                        System.out.println("height: "+branch.getHeight());
                        System.out.println("width: "+branch.getWidth());
                        System.out.println("");
                        System.out.println("superiorBranch: "+superiorBranch.getName());
                        System.out.println("height: "+superiorBranch.getHeight());
                        System.out.println("width: "+superiorBranch.getWidth());

                        superiorBranch.setWidth(superiorBranch.getWidth() + branch.getWidth());
                        System.out.println("superiorBranch is Parallel type");
                        System.out.println("now superiorBranch width set to: "+superiorBranch.getWidth());

                        if (branch.getHeight() > superiorBranch.getHeight())
                        {
                            superiorBranch.setHeight(branch.getHeight());
                            System.out.println("branch height is more than superiorBranch height");
                            System.out.println("now superiorBranch height set to: "+superiorBranch.getHeight());

                        }
                        else
                        {
                            System.out.println("XX branch height is NOT more than superiorBranch height");
                        }
                    }
                    else
                    {
                        System.out.println("");
                        System.out.println("branch: "+branch.getName());
                        System.out.println("height: "+branch.getHeight());
                        System.out.println("width: "+branch.getWidth());
                        System.out.println("");
                        System.out.println("superiorBranch: "+superiorBranch.getName());
                        System.out.println("height: "+superiorBranch.getHeight());
                        System.out.println("width: "+superiorBranch.getWidth());
                        superiorBranch.setHeight(superiorBranch.getHeight() + branch.getHeight());
                        System.out.println("superiorBranch is series type");
                        System.out.println("now superiorBranch height set to: "+superiorBranch.getHeight());

                        if (branch.getWidth() > superiorBranch.getWidth())
                        {
                            superiorBranch.setWidth(branch.getWidth());
                            System.out.println("branch width is more than superiorBranch width");
                            System.out.println("now superiorBranch width set to: "+superiorBranch.getWidth());

                        }
                        else
                        {
                            System.out.println("XX branch width is NOT more than superiorBranch width");
                        }
                    }
                    branch.setVisited();
                }
            }




        }

        /*ArrayList<Branch>   superiorBranches = new ArrayList<>();

        for (Branch branch : branches)
        {
            if ( branch.getSuperiorBranch() != null && !superiorBranches.contains(branch.getSuperiorBranch()) )
            {
                superiorBranches.add(branch.getSuperiorBranch());
            }
        }
        if (superiorBranches.size() != 0)
        {
            processSettingUpTheBranchesHeightAndWidth(superiorBranches);
        }*/

        int visitedBranches = 0;
        for (Branch branch : branches)
        {
            if (branch.getIsVisited())
            {
                visitedBranches++;
            }
        }
        if (visitedBranches != branches.size())
        {
            processSettingUpTheBranchesHeightAndWidth(branches);
        }

    }

    private static void setBranchesHeightAndWidth()
    {
        setBranchesNotVisited(getAllBranchesAndSubbranches());

        ArrayList<Branch>   allBranchesExceptFinalSuperior = new ArrayList<>();
        allBranchesExceptFinalSuperior = getAllBranchesAndSubbranches();
        allBranchesExceptFinalSuperior.remove(controller.getFinalSuperiorBranch());

        processSettingUpTheBranchesHeightAndWidth(allBranchesExceptFinalSuperior);

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
            putImage2(wire, nodes[i], nodes[i - 1]);
        }
    }

    private static void setMaxHorizontalAndVerticalNodes()
    {
        maxHorizontalNodes = controller.getFinalSuperiorBranch().getWidth()  ;
        maxVerticalNodes = controller.getFinalSuperiorBranch().getHeight() + 1  ;
    }

    private static void drawTheNodes()
    {
        for (int i = 1; i <= maxHorizontalNodes; i++) {
            for (int j = 1; j <= maxVerticalNodes; j++) {
                circuitPane.getChildren().add(new Circle(getXY(i), getXY(j), 2));
            }
        }
    }

    private static PaneNode convertNodeNumToPaneNode(double num) {
        double x = num % maxVerticalNodes != 0 ? num % maxVerticalNodes : maxVerticalNodes;
        double y = maxVerticalNodes - Math.ceil(num / maxVerticalNodes );
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
        imageView.setLayoutX(getXY(maxHorizontalNodes/2));
        imageView.setLayoutY(getXY(maxVerticalNodes));
        circuitPane.getChildren().add(imageView);
    }

    private static void putImage(Image image, PaneNode first , PaneNode second )
    {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(drawingUnitLength);
        imageView.setFitHeight(drawingUnitLength);

        if (first.nodeX == second.nodeX && Math.abs(first.nodeY - second.nodeY) == getXY(1)) {
            if (first.nodeY > second.nodeY) {
                imageView.setRotate(180);
            }
            PaneNode middlePoint = new PaneNode(first.nodeX, (first.nodeY + second.nodeY) / 2);
            PaneNode node = getLayoutNode(middlePoint);
            imageView.setLayoutX(node.nodeX);
            imageView.setLayoutY(node.nodeY);
            circuitPane.getChildren().add(imageView);
        } else if (first.nodeY == second.nodeY && Math.abs(first.nodeX - second.nodeX) == getXY(1)) {
            if (first.nodeX < second.nodeX) {
                imageView.setRotate(-90);
            } else {
                imageView.setRotate(90);
            }
            PaneNode middlePoint = new PaneNode((first.nodeX + second.nodeX) / 2, first.nodeY);
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

    private static void putWire(PaneNode first , PaneNode second)
    {

        ImageView imageView = new ImageView(wire);
        if (first.nodeX == second.nodeX)
        {
            imageView.setFitWidth(drawingUnitLength);
            imageView.setFitHeight(Math.abs(first.nodeY - second.nodeY));
        }
        if (first.nodeY == second.nodeY)
        {
            imageView.setFitWidth(drawingUnitLength);
            imageView.setFitHeight(Math.abs(first.nodeX - second.nodeX));
        }

        if (first.nodeX == second.nodeX && Math.abs(first.nodeY - second.nodeY) == getXY(1)) {
            if (first.nodeY > second.nodeY) {
                imageView.setRotate(180);
            }
            PaneNode middlePoint = new PaneNode(first.nodeX, (first.nodeY + second.nodeY) / 2);
            PaneNode node = getLayoutNode(middlePoint);
            imageView.setLayoutX(node.nodeX);
            imageView.setLayoutY(node.nodeY);
            circuitPane.getChildren().add(imageView);
        } else if (first.nodeY == second.nodeY && Math.abs(first.nodeX - second.nodeX) == getXY(1)) {
            if (first.nodeX < second.nodeX) {
                imageView.setRotate(-90);
            } else {
                imageView.setRotate(90);
            }
            PaneNode middlePoint = new PaneNode((first.nodeX + second.nodeX) / 2, first.nodeY);
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





    private static void putImage2(Image image, double numNode1, double numNode2) {
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


    public static ArrayList<Branch> getAllBranchesTemp() {
        return allBranchesTemp;
    }

    public static ArrayList<Branch> getNewSeriesBranches() {
        return newSeriesBranches;
    }

    public static ArrayList<Branch> getNewTempBranch() {
        return newTempBranch;
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