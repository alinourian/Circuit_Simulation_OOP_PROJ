package view;

import view.file.FileInputProcessor;

public abstract class Errors {
    public static String string;

    public static void tranError1(int lineNum) {
        string = "error :\n" +
                "Line " + lineNum + " : <.tran> is in wrong line";
        System.err.println("error :");
        System.err.println("Line " + lineNum + " : <.tran> is in wrong line");
        //MainPageController.printResult(string, false);
    }

    public static void tranError2(int lineNum) {
        string = "error :\n" +
                "Line " + lineNum + " : <.tran> not found!";
        System.err.println("error :");
        System.err.println("Line " + lineNum + " : <.tran> not found!");
        //MainPageController.printResult(string, false);
    }

    public static void constantsError() {
        string = "error -1 :\n" +
                "<dV, dI, dT> not initialised correctly!";
        System.err.println("error -1 :");
        System.err.println("<dV, dI, dT> not initialised correctly!");
        //MainPageController.printResult(string, false);
    }

    public static void fileUpload() {
        string = "File successfully uploaded.";
        System.out.println("File successfully uploaded.");
        //MainPageController.getInstance().getErrorTextArea().setText(string);
        //MainPageController.printResult(string, true);
    }

    public static void saveFileError(Exception e) {
        string = "" + e + "\n" +
                "Can not save on file";
        System.err.println("" + e);
        System.err.println("Can not save on file");
        //MainPageController.printResult(string, false);
    }

    public static void commandError(int lineNum) {
        string = "error :\n" +
                "Line " + lineNum + " : Invalid command!";
        System.err.println("error :");
        System.err.println("Line " + lineNum + " : Invalid command!");
        //MainPageController.printResult(string, false);
    }

    public static void similarNameError(int lineNum) {
        string = "error :\n" +
                "Line " + lineNum + " : Similar name found!";
        System.err.println("error :");
        System.err.println("Line " + lineNum + " : Similar name found!");
        //MainPageController.printResult(string, false);
    }

    public static void valueError(int lineNum) {
        string = "error :\n" +
                "Line " + lineNum + " : Invalid value!";
        System.err.println("error :");
        System.err.println("Line " + lineNum + " : Invalid value!");
        //MainPageController.printResult(string, false);
    }

    public static void branchError(int lineNum) {
        string = "error :\n" +
                "Line " + FileInputProcessor.COMMANDLINE + " : Controller branch does not initialize!";
        System.err.println("error :");
        System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Controller branch does not initialize!");
        //MainPageController.printResult(string, false);
    }

    public static void nodeError(int lineNum) {
        string = "error :\n" +
                "Line " + FileInputProcessor.COMMANDLINE + " : Controller nodes do not initialize!";
        System.err.println("error :");
        System.err.println("Line " + FileInputProcessor.COMMANDLINE + " : Controller nodes do not initialize!");
        //MainPageController.printResult(string, false);
    }

    public static void groundError() {
        string = "ground not found!";
        System.err.println("ground not found!");
        //MainPageController.printResult(string, false);
    }

    public static void exceptionsError(Exception e) {
        string = "" + e;
        System.err.println("" + e);
        //MainPageController.printResult(string, false);
    }

    public static void print(String text) {
        System.out.println(text);
        //MainPageController.getInstance().printActions(text);
    }
}
