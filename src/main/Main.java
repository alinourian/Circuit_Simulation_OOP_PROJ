package main;

import controller.Controller;

public class Main {
    public static void main(String[] args) {
        String s_value = "154";
        char unit = s_value.charAt(s_value.length() - 1);
        double value = Double.parseDouble(s_value.substring(0, s_value.length() - 1)) * Controller.getInstance().getUnit(unit);
        System.out.println(unit);
        System.out.println(value);
    }
}
