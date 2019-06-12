package learnapp.service;

public class RouteService {
    private static String theme;
    private static String subTheme;
    private static String theory;
    private static String practice;
    private static String currentState;

    public static String getTheme() {
        return theme;
    }

    public static void setTheme(String theme) {
        RouteService.theme = theme;
    }

    public static String getSubTheme() {
        return subTheme;
    }

    public static void setSubTheme(String subTheme) {
        RouteService.subTheme = subTheme;
    }

    public static String getTheory() {
        return theory;
    }

    public static void setTheory(String theory) {
        RouteService.theory = theory;
    }

    public static String getPractice() {
        return practice;
    }

    public static void setPractice(String practice) {
        RouteService.practice = practice;
    }

    public static String getCurrentState() {
        return currentState;
    }

    public static void setCurrentState(String currentState) {
        RouteService.currentState = currentState;
    }
}
