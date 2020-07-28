package view;

import view.file.FileInputProcessor;

public abstract class Errors {
    public static String string;

    public static void tranError1(int lineNum) {
        string = "error :\n" +
                "Line " + lineNum + " : <.tran> is in wrong line";
        System.err.println(string);
        //MainPageController.printResult(string, false);
    }

    public static void tranError2(int lineNum) {
        string = "error :\n" +
                "Line " + lineNum + " : <.tran> not found!";
        System.err.println(string);
        //MainPageController.printResult(string, false);
    }

    public static void constantsError() {
        string = "error -1 :\n" +
                "<dV, dI, dT> not initialised correctly!";
        System.err.println(string);
        //MainPageController.printResult(string, false);
    }

    public static void fileUpload() {
        string = "File successfully uploaded.";
        System.out.println(string);
        //MainPageController.getInstance().getErrorTextArea().setText(string);
        //MainPageController.printResult(string, true);
    }

    public static void saveFileError(Exception e) {
        string = "" + e + "\n" +
                "Can not save on file";
        System.err.println(string);
        //MainPageController.printResult(string, false);
    }

    public static void commandError(int lineNum) {
        string = "error :\n" +
                "Line " + lineNum + " : Invalid command!";
        System.err.println(string);
        //MainPageController.printResult(string, false);
    }

    public static void similarNameError(int lineNum) {
        string = "error :\n" +
                "Line " + lineNum + " : Similar name found!";
        System.err.println(string);
        //MainPageController.printResult(string, false);
    }

    public static void valueError(int lineNum) {
        string = "error :\n" +
                "Line " + lineNum + " : Invalid value!";
        System.err.println(string);
        //MainPageController.printResult(string, false);
    }

    public static void branchError(int lineNum) {
        string = "error :\n" +
                "Line " + FileInputProcessor.COMMANDLINE + " : Controller branch does not initialize!";
        System.err.println(string);
        //MainPageController.printResult(string, false);
    }

    public static void nodeError(int lineNum) {
        string = "error :\n" +
                "Line " + FileInputProcessor.COMMANDLINE + " : Controller nodes do not initialize!";
        System.err.println(string);
        //MainPageController.printResult(string, false);
    }

    public static void groundError() {
        string = "ground not found!";
        System.err.println(string);
        //MainPageController.printResult(string, false);
    }

    public static void exceptionsError(Exception e) {
        string = "" + e;
        System.err.println(string);
        //MainPageController.printResult(string, false);
    }

    public static void transitionFailed() {
        string = "Transition failed!\n" +
                "Please check your simulations constant.\n" +
                "They're not fit!";
        System.err.println(string);
    }

    public static void print(String text) {
        System.out.println(text);
        //MainPageController.getInstance().printActions(text);
    }
}
