package view.fxml;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class HelpPageController implements Initializable {


    private static final javafx.stage.Stage Stage = new Stage();

    @FXML private TextArea firstTextArea;
    @FXML private TextArea secondTextArea;
    @FXML private TextArea thirdTextArea;

    private final String text = "College of Electronic Engineering, Naval University of Engineering,WuHan,430033, China 2\n" +
                "Equipment Department of the Navy, Beijing,100055, China 3\n" +
                "School of electrical information engineering, North China University of Technology, Beijing, 100144, China\n" +
                "*Corresponding author, e-mail: chenbing0710@126.com\n" +
                "Abstract\n" +
                "Knowledge of the behavior of electrical circuits requires the simultaneous solution of a\n" +
                "number of equations.The model creation [1], circuit simulation [2] and testing [3], and application\n" +
                "of analog circuit and VLSI [4] require the assisstance of simulation tools. As a general-purpose\n" +
                "circuit-simulation program that solves the network equations for the node voltage for nonlinear\n" +
                "DC, nonlinear transient, and linear AC analysis, SPICE in its different versions has been the\n" +
                "main computer-aided analysis program used in analog design, and diagnosis for researchers,\n" +
                "printing circuit board and electrical device manufacture for engineering and in universities and\n" +
                "colleges for student education for over 40 years. This widely used spice-softwares, such as\n" +
                "pSpice, hSpice, ngSpice, shares same engine which was re-developed or modified on the base\n" +
                "of Berkeley's kernel and to provide various interfaces and functions.\n" +
                "However, this commercial software cannot be used to develop customized software\n" +
                "free. In fact, it is well known that SPICE engine is open-source and free use by researchers all\n" +
                "over the world. To make customized simulation software, researchers had various customer\n" +
                "modification after analysis of the simulation algorithms [5],data structure and code structure. In\n" +
                "1980s, SPICE with version 2 had already rewrited in C code and ported in PC [6]. The 1990s\n" +
                "saws various expansion of SPICE with multimedia technology [7] and networks [8, 9] with its\n" +
                "application in engineer and education. In the later two decades, the simulation algorithms were\n" +
                "continously modified to speed the convergence [10, 11] and improve the accuracy [12]. In the\n" +
                "new centery, various virtual circuit labaroty [13, 14] and studies [15-17] had been build by the\n" +
                "aid of these customized spice-like simulations. To the author's knowledge, there is little\n" +
                "information aviable about the computation principle and its simulations of the SPICE engine.\n" +
                "Further, the detail information about the re-development of the simulation software with spice\n" +
                "engine and C++ is also helpful to programmers.\n" +
                "non-linear components into some equivalence circuit suitable for Nodal Analysis, and then find a\n" +
                "solution as if it was a linear circuit, but using a little guessing and a lot more trips. This process\n" +
                "covers step 2-5, i.e. the inner loop.\n" +
                "As an example, the circuit shown in Figure 2 includes a very non-linear component, a\n" +
                "diode.\n" +
                "Figure 2. A Simple Non-linear Diode Circuit and part of its equivalent circuit\n" +
                "Diode's current versus voltage relationship is described by:\n" +
                "Where IS is the saturation current and Vt is the thermal voltage. A diode can be\n" +
                "modeled using linear components -- a parallel combination of a conductance Geq and a current\n" +
                "source Ieq, where Geq is simply the slope of the tangentat the operating point Vdo.\n" +
                "And Ieq is the point where the tangent slices through the y-axis.\n" +
                "After this equivalent substition, the nodal equations can be developed as:\n" +
                "The whole solution looks like this:\n" +
                "1) Guess the diode's initial trial operating point.\n" +
                "2) Create linear companion models.\n" +
                "3) Solve Nodal Equations.\n" +
                "4) Convergence?\n" +
                "|Vn - Vn-1|<Vlimit and |In - In-1| <Ilimit ?\n" +
                "5) No--Use the calculated diode voltage as new trial operating point for another\n" +
                "iteration. Start again at Step 2.\n" +
                "6) Yes - Stop iterations and find the solution.\n" +
                "In SPICE, the limits of voltage and currentare actually calculated by[18].\n" +
                "SPICE executed the outer loop for the linear circuit transient analysis. If there is any\n" +
                "energy-storage component, such as capacitor and inductor, SPICE first transformed the energystorage " +
                "components into their linear companion models, and used Nodal Analysis to find the\n" +
                "solution. For example, for the capacitors, SPICE will use backward-Euler(BE) to predict the next\n" +
                "approximate value at a future time point tn+1. The idea of EU is predicting the next voltage with\n" +
                "the slope at xn+1 as the following equation:\n" +
                "Graphically, it looks something like Figure 3.\n" +
                "Figure 3. Numeric integration applied to approximate the next voltage\n" +
                "SPICE will first try to transform an energy-storage component into its equivalent linear\n" +
                "component. For example, a capacitor is transformed using a two step process:\n" +
                "The relationships of a capacitor's voltage-current-charge can be described as:\n" +
                "Next, apply the BE formula to predict the capacitor's voltage at the next time point.\n" +
                "With this equivalent substitution, SPICE can develop the nodal equations and find the\n" +
                "every node voltages for certain time. Then, SPICE would forward a time step and repeat the\n" +
                "same procedure to find the voltages at that time, and so on till the last time.\n" +
                "SPICE can set timestepdynamically to trade offsimulation accuracy and speed. If the\n" +
                "change of current or voltage is rapid, SPICE would reduce its step to increase the precise of the\n" +
                "simulation, and vice versa.\n" +
                "3. SPICE Code Strcture\n" +
                "The simplified block diagram of SPICE engine code structure can be shown in Figure 5,\n" +
                "where seven Modules are included.\n" +
                "1) Command Parsing: The kernel of this block is comn structure, which includes the name of\n" +
                "the commands and their corresponding functions. The program implemented an array of\n" +
                "comn structure that defines the commands which SPICE can parse and its corresponding\n" +
                "actions. Whenever a command was input, SPICE parses it and finds its match from the set\n" +
                "of commands, and executes the corresponding action.\n" +
                "4) Numerical Computation: This block contains two typical numerical methods: NewtonRaphson iteration " +
                "and numerical integration. In SPICE, they were used to transform the set\n" +
                "of differential equations into a set of algebraic equations.\n" +
                "Figure 5. Code Structure of SPICE engine\n" +
                "6) Device Block contains two structures, DEV mode land SPICE dev.DEV model lists the\n" +
                "models of the device types used in given circuit and SPICE dev contains the complete\n" +
                "information of every component in the circuit.\n" +
                "7) Interface: this block is designed to be a generalized and open structure, and used to\n" +
                "communicate between the Front and end of simulation system.\n" +
                "4. Application Interface of SPICE engine\n" +
                "SPICE engine can be package to a number of independent code procedures with\n" +
                "prescribed interface. In this way, a block can call certain package to complete given work\n" +
                "according to the interface. In addition, any block can be maintained, updated，even rewritten\n" +
                "independently.\n" +
                "Basically, SPICE operates like this:\n" +
                "1) SPICE reads in a text circuit description file (“.cir” extension) called a netlist, and then sets\n" +
                "the corresponding Parameters.\n" +
                "2) SPICE sets the simulation options and performed certain analysis type as given (AC,\n" +
                "Transient, DC, Noise, etc.) by calling the interface of analysis procedure.\n" +
                "Let's take transient (time) analysis for Linear Circuits as an example to investigate.\n" +
                "SPICE would call fuctionDCTran first with the given circuit description. Inside this function, and\n" +
                "device information would be found according to every device model. An operating point is \n" +
                "TELKOMNIKA ISSN: 1693-6930 \n" +
                "SPICE Engine Analysis and Circuit Simulation Application Development (Bing Chen)\n" +
                "69\n" +
                "required for the initial solution to a Transient Analysis. After the initial operating point was found,\n" +
                "this function set the timestep and option parameter call the inner loop to solve the Nodal\n" +
                "equation. The results is acquired from the sparse matrix and stored in the structure TRANan,\n" +
                "where it can be print or draw by \".PRINT\" or\".PLOT\" command.\n" +
                "5. Experiments of using SPICE Engine\n" +
                "SPICE engine can interactive with end user by command lines only. Therefore, it's\n" +
                "convenient to encapsulate the engine to dynamic link libraries in the integrated development\n" +
                "environment like visual studio 2010. These libraries were then package to be a engine class\n" +
                "with exposing appropriate interfaces to the caller. For instance, the interface function for the\n" +
                "transient analysis described previously may look like \"BOOL tran (char* filename, int step,\n" +
                "intend_t, intstart_t, intmaxstep)\", where the input parameter can be the file name and path of\n" +
                "circuit description, timestep, endtime, starttime (with the default value zero), and max timestep\n" +
                "allowed. This function returns TURE when it completes the analysis and return FALSE when\n" +
                "any error was found.\n";


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        firstTextArea.setText(text);
        secondTextArea.setText(text);
        thirdTextArea.setText(text);
    }

    public static javafx.stage.Stage getStage() {
        return Stage;
    }
}
