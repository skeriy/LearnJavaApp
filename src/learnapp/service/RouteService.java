package learnapp.service;

import java.util.ArrayList;
import java.util.Map;

public class RouteService {
    private static Integer theme;
    private static Integer subTheme;
    private static Integer theory;
    private static Integer practice;
    private static String currentState;

    private static Integer minTheory = 1;
    private static Integer minPractice = 1;
    private static Integer maxTheory;
    private static Integer maxPractice;

    public static void incTheme() {
        theme++;
    }

    public static void incSubTheme() {
        subTheme++;
    }

    public static void incTheory() {
        theory++;
    }

    public static void incPractice() {
        practice++;
    }

    public static Integer getTheme() {
        return theme;
    }

    public static void setTheme(Integer theme) {
        RouteService.theme = theme;
    }

    public static Integer getSubTheme() {
        return subTheme;
    }

    public static void setSubTheme(Integer subTheme) {
        RouteService.subTheme = subTheme;
    }

    public static Integer getTheory() {
        return theory;
    }

    public static void setTheory(Integer theory) {
        RouteService.theory = theory;
    }

    public static Integer getPractice() {
        return practice;
    }

    public static void setPractice(Integer practice) {
        RouteService.practice = practice;
    }

    public static String getCurrentState() {
        return currentState;
    }

    public static void setCurrentState(String currentState) {
        RouteService.currentState = currentState;
    }

    public static Integer getMinTheory() {
        return minTheory;
    }

    public static void setMinTheory(Integer minTheory) {
        RouteService.minTheory = minTheory;
    }

    public static Integer getMaxTheory() {
        return maxTheory;
    }

    public static void setMaxTheory(Integer maxTheory) {
        RouteService.maxTheory = maxTheory;
    }

    public static Integer getMinPractice() {
        return minPractice;
    }

    public static void setMinPractice(Integer minPractice) {
        RouteService.minPractice = minPractice;
    }

    public static Integer getMaxPractice() {
        return maxPractice;
    }

    public static void setMaxPractice(Integer maxPractice) {
        RouteService.maxPractice = maxPractice;
    }
}
