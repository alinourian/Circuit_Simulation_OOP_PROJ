package view.fxml;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class HelpPageController {


    private static final javafx.stage.Stage Stage = new Stage();

    @FXML
    private TextArea textArea;

    private static final String text = "TELKOMNIKA, Vol.14, No.1, March 2016, pp. 64~71\n" +
                "ISSN: 1693-6930, accredited A by DIKTI, Decree No: 58/DIKTI/Kep/2013\n" +
                "DOI: 10.12928/TELKOMNIKA.v14i1.283264\n" +
                "\n" +
                "Received October 12, 2014; Revised February 16, 2016; Accepted February 27, 2016\n" +
                "SPICE Engine Analysis and Circuit Simulation\n" +
                "Application Development\n" +
                "Bing Chen*1\n" +
                ", Gang Lu2\n" +
                ", Yuehai Wang3\n" +
                "1\n" +
                "College of Electronic Engineering, Naval University of Engineering,WuHan,430033, China 2\n" +
                "Equipment Department of the Navy, Beijing,100055, China 3\n" +
                "School of electrical information engineering, North China University of Technology, Beijing, 100144, China\n" +
                "*Corresponding author, e-mail: chenbing0710@126.com\n" +
                "Abstract\n" +
                "Electrical design automation plays an important role in nowadays electronic industry. Various\n" +
                "commercial Simulation Program with Integrated Circuit Emphasis (SPICE) packages, such as pSpice Or\n" +
                "CAD, have become the standard computer program for electrical simulation, with numerous copies in use\n" +
                "worldwide. The customized simulation software with copyright need the understanding and using of SPICE\n" +
                "engine which was open-source shortly after its birth. The inner workings of SPICE, including algorithms,\n" +
                "data structure and code structure of SPICE were analyzed, and a engine package and application\n" +
                "development approach were proposed. The experiments verified its feasibility and accuracy.\n" +
                "Keywords: SPICE engine analysis, Circuit Simulation Application, Simulation analysis\n" +
                "Copyright © 2016 Universitas Ahmad Dahlan. All rights reserved.\n" +
                "1. Introduction\n" ;
                /*+
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
                "In this paper, we conducted this study to analyze the inner workings of SPICE engine\n" +
                "and customized simulation software development based on the spice kernel. The experiments\n" +
                "verified the feasibility and accuracy of the proposed approach. \n" +
                "TELKOMNIKA ISSN: 1693-6930" +
                "SPICE Engine Analysis and Circuit Simulation Application Development (Bing Chen)\n" +
                "65\n" +
                "2. Inside of SPICE\n" +
                "2.1. Introduction of SPICE\n" +
                "SPICE stands for Simulation Program with Integrated Circuit Emphasis. It was born as\n" +
                "a class project at the University of California, Berkeley during the mid-1970s and first released\n" +
                "in 1971. After its first presented at a conference in 1973, this invaluable program soon evolved\n" +
                "to universally used software and then to become the worldwide standard integrated circuit\n" +
                "simulator in 1980s. Now SPICE is a general-purpose, open source analog electronic circuit\n" +
                "simulator used in integrated circuit and board-level design to check the integrity of circuit\n" +
                "designs, to predict circuit behavior, and even to locate the potential errors in circuit board.\n" +
                "2.2. SPICE Algorithm Overview\n" +
                "Figure 1 has given a simplified block diagram of the main SPICE program flow. We can\n" +
                "see from Figure 1, when a circuit description was input to SPICE, its initial operating point was\n" +
                "calculated first, and the linear companion models was then created for non-linear devices. Then\n" +
                "SPICE entered its first kernel process--block 3 and 4, which stand for Nodal Analysis\n" +
                "accomplished by formulating the Nodal Matrix and solving the nodal equations for the circuit\n" +
                "voltages. SPICE finds the solution for Non-linear circuits in the inner loop (2-6). It may take\n" +
                "many iterations before the calculations converge to a solution. The outer loop (7-9), together\n" +
                "with the inner loop, performs a Transient Analysis creating equivalent linear models for energystorage " +
                "components for capacitors, inductors, etc. and selecting the best time points.\n" +
                "Figure 1. The Flowchart of SPICE Algorithm\n" +
                "2.2.1. Nodal Analysis\n" +
                "At the core of the SPICE engine is a basic technique called Nodal Analysis that\n" +
                "calculates the voltage at any node given all resistances (conductances) and current sources of\n" +
                "the circuit. When a circuit was input, as shown in Figure 2, the SPICE engine calculate the the\n" +
                "set of nodal equations according Kirchoff Law. These set of equations will be conveted to matrix\n" +
                "form using mathmatical analysis and numeric computing. The frequently used method is\n" +
                "Gaussian Elimination and LU Factorization. At last, the equation is solved for the node voltages\n" +
                "and this equation is the central mechanism of the SPICE algorithm.\n" +
                "2.2.2. Non-Linear DC Analysis\n" +
                "SPICE can find the voltage at every node in a DC linear circuit by only executing mode\n" +
                "3 and 4. If any non-linear components appear in the circuit, SPICE will first transforming the \n" +
                " ISSN: 1693-6930\n" +
                "TELKOMNIKA Vol. 14, No. 1, March 2016 : 64 – 71\n" +
                "66\n" +
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
                "Vlimit = Vn * RELLOT+VNTOL\n" +
                "Ilimit = In * RELLOT+ABSTOL\n" +
                "By default, RELTOL is set to 0.001 or 0.1 percent. So if the expected voltage is 5V, the\n" +
                "Vlimit should be set to 5 mV to reach a solution. However, if the excepted voltage swings near\n" +
                "zero, Vlimit would be ridiculous small and hard to be reached. That's where VNTOL enters the \n" +
                "TELKOMNIKA ISSN: 1693-6930 " +
                "SPICE Engine Analysis and Circuit Simulation Application Development (Bing Chen)\n" +
                "67\n" +
                "picture. The default of VNTOL is 1 μV to ensures that the limit doesn't get too small if voltages\n" +
                "approach 0V. RELTOL and ABSTOL play a similar role for the current change limits,and the\n" +
                "default for ABSTOL is 1PA.\n" +
                "2.2.3. Transient Analysis\n" +
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
                "ௗ௧ (7)\n" +
                "Finally, we can get an equation in terms of voltages and currents only:\n" +
                "Figure 4. A Transient Analysis Circuit \n" +
                " \n ISSN: 1693-6930\n" +
                "TELKOMNIKA Vol. 14, No. 1, March 2016 : 64 – 71\n" +
                "68\n" +
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
                "2) Circuit Description: The most important data structure of this block is CKT circuit structure,\n" +
                "where contains the device type, simulation type, temperature, and node information, etc.\n" +
                "3) Sparse Matrix: This block defines SMP element structure to represent the non-zero items of\n" +
                "sparse matrix where the items are stored with increasing order as they appear in the row or\n" +
                "column of The matrix.\n" +
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
                "any error was found.\n" +
                "Then various applications about different circuit analysis and fault simulation can be\n" +
                "developed with the SPICE engine that was packaged as a class. Here we build a single\n" +
                "document MFC application that calls the SPICE engine. It reads a netlist file as input, and sets\n" +
                "analysis type, simulation parameters by a dialogue. Then the application calls the engine to\n" +
                "simulate the behavior of the inputting circuit. The simulation results were outputted as text file.\n" +
                "Figure 6 shows the application running interface. It displays a operator amplified circuit in the\n" +
                "window frame and reads user defined analysis parameters.\n" +
                "Figure 6. Simulation Application Example\n" +
                "Figure 7. OP AMP circuit under testing \n" +
                " ISSN: 1693-6930\n" +
                "TELKOMNIKA Vol. 14, No. 1, March 2016 : 64 – 71\n" +
                "70\n" +
                "To verify the feasibility of this approach and investigate the accuracy of SPICE engine,\n" +
                "the same circuit was build and analyzed with the same parameters and analysis type in OrCAD\n" +
                "Capture, a famous business software. Figure 7 is schematic Of the testing circuit in orCAD\n" +
                "Capture.\n" +
                "These two groups of simulation data were exported to Matlab to investigate the\n" +
                "difference. Fig 8 is result given by Matlab where the solid line represent the results from OrCAD\n" +
                "Capture, and the dot line represent the results from the testing application. It is clear that the\n" +
                "results are in accord with each other in most case. There are also some difference due to the\n" +
                "possible difference in device models, and maybe integration methods. The most significant\n" +
                "difference lie to the maximumValue with only 2.13% difference.\n" +
                " Figure 8. Experiment result\n" +
                "As the result, our approach of using SPICE engine turns out to be a suitable for further\n" +
                "software development.\n" +
                "6. Conclusion\n" +
                "In this paper, the kernel algorithm and code structure of SPICE engine were analyzed,\n" +
                "and an software architecture that package SPICE engine as class and called by other\n" +
                "applications is proposed. The experiment verified the feasibility and accuracy of the proposed\n" +
                "approach. This research can be used to development customized simulation software with fully\n" +
                "simulation typies and all customered parameters aviable. Furthermore, it can easily used to\n" +
                "develop the fault simulation by simply replace the function model of the devices as a faultinjected model.\n" +
                "Further works involves two parts. One is details of engine, such as its algorithm, its\n" +
                "analysis, its parameter setting. Theother is result data processing, such as Fourier transform,\n" +
                "wavelet analysis and featureextraction.\n" +
                "References\n" +
                "[1] Hamiane M, Ahlia U. A CMOSbased Analog Function Generator: HSPICE Modeling and Simulation.\n" +
                "International Journal of Electrical and Computer Engineering (IJECE). 2014; 4(4): 532-538.\n" +
                "[2] Xiaoyuan W, Zhe Y. Simulation of ZVS Converter and Analysis of Its Switching Loss Based on\n" +
                "PSPICE. International Journal of Power Electronics and Drive Systems (IJPEDS). 2012; 2(1): 19-24.\n" +
                "[3] Autee R. Dynamic Testing & Simulation of 4 KW 55 Hp Switched Reluctance Motor using PSpice.\n" +
                "International Journal of Power Electronics and Drive Systems (IJPEDS). 2011; 1(1): 36-40.\n" +
                "[4] Srivastava A, Palavali SR. Integration of SPICE with TEK LV511 ASIC design verification system.\n" +
                "Proceedings of the 36th Midwest Symposium on Circuits and Systems, August 16, 1993 - August 18,\n" +
                "1993. Detroit, MI, USA. 1993.\n" +
                "[5] Nichols KG, Kazmierski TJ, Zwolinski M, Brown AD. Overview of SPICE-like circuit simulation\n" +
                "algorithms. IEE Proceedings: Circuits, Devices and Systems. 1994; 141(4): 242-250.\n" +
                "[6] Zhao Y, Gao G. The Implementaton of SPICE2 with the Graphic Pre- and Post-Processors on PC.\n" +
                "Chinese Journal of Microelectronics and computer. 1988; 09: 12-14.\n" +
                "[7] Conrad A. Interactive spice program organizes circuit designs. Microwaves &amp RF. 1995; 34(4):\n" +
                "183-184.\n" +
                "[8] Regnier J, Wilamowski B. SPICE simulation and analysis through Internet and intranet networks.\n" +
                "IEEE Circuits and Devices Magazine. 1998; 14(3): 9-12. \n" +
                "TELKOMNIKA ISSN: 1693-6930\n" +
                "SPICE Engine Analysis and Circuit Simulation Application Development (Bing Chen)\n" +
                "71\n" +
                "[9] Wilamowski B, Regnier JR, Malinowski A. SIP - Spice Intranet Package. Proceedings of the 1998\n" +
                "International Symposium on Industrial Electronics, ISIE. Pretoria, South africa. 1998.\n" +
                "[10] Zuberek WM, Zuberek MS. Table-driven circuit elements in SPICE-like simulation programs. " +
                "TwentyThird Annual Asilomar Conference on Signals, Systems &amp; Computers. Pacific Grove, CA, USA.\n" +
                "1989.\n" +
                "[11] Zhou TY, Liu H, Zhou D, Tarim T. A fast analog circuit analysis algorithm for design modification and\n" +
                "verification. SPECIAL SECTION ON THE 2010 INTERNATIONAL SYMPOSIUM ON PHYSICAL\n" +
                "DESIGN. Piscataway, NJ. 2011.\n" +
                "[12] Zhang X, Liu G, Guo Y. A Circuit Optimization System Based on SPICE. Chinese Journal of\n" +
                "Hangzhou Institute of Electronic Engineering. 2001; 03: 33-37.\n" +
                "[13] Mao S, Dong Y, Zhu M. Construction of the Virtual Experimental Platform of Electronic Circuits Based\n" +
                "on Spice. Chinese Journal of Computer Application and Software. 2006; 23(03): 17-19.\n" +
                "[14] Yang Y, Zhao J, Wu W. Design of Circuit Simulation Software Based on Spice Secondary\n" +
                "Development. Chinese Journal of Computer Simulation. 2007; 24(05): 268-323.\n" +
                "[15] Beams JD. Adding SPICE to software development: A software development approach designed for\n" +
                "rapidly changing environments. Proceedings of the 1995 ACM Symposium on Applied Computing.\n" +
                "Nashville, TN, USA. 1995.\n" +
                "[16] Zhuang H, He F, Lin X, Zhang L, Zhang J, Zhang X, et al. A web-based platform for nanoscale " +
                "nonclassical device modeling and circuit performance simulation. 2009 International Conference on Web\n" +
                "Information Systems and Mining, WISM. Shanghai, China. 2009.\n" +
                "[17] Zhuang H, He J, Deng W, Zhang X, Zhu X, He Q, et al. A web-based education platform for\n" +
                "nanoscale device modeling and circuit simulation. 4th Interdisciplinary Engineering Design Education\n" +
                "Conference, IEDEC. Santa Clara, CA, United states. 2014.\n" +
                "[18] Nenzi P, Vogt H. Spice Users Manual. 2012.";

                 */
    public void onFire() {
        textArea.setText(text);
    }

    public static javafx.stage.Stage getStage() {
        return Stage;
    }
}
