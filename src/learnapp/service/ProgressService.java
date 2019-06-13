package learnapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ProgressService {
    private static int theme;
    private static int subTheme;
    private static int theory;
    private static int practice;
    private static String currentState;

    public static void init() {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get("data/progress.json"));
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonData);

            setTheme(rootNode.get("theme").asInt());
            setSubTheme(rootNode.get("sub_theme").asInt());
            setTheory(rootNode.get("theory").asInt());
            setPractice(rootNode.get("practice").asInt());
            setCurrentState(rootNode.get("current").asText());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static void switchState() {
        currentState = currentState.equals("theory") ? "practice" : "theory";
    }

    public static void incPractice() {
        theme++;
    }

    public static void incTheme() {
        subTheme++;
    }

    public static void incSubTheme() {
        theory++;
    }

    public static void incTheory() {
        practice++;
    }

    public static String getCurrentState() {
        return currentState;
    }

    public static void setCurrentState(String currentState) {
        ProgressService.currentState = currentState;
    }

    public static int getTheme() {
        return theme;
    }

    public static void setTheme(int theme) {
        ProgressService.theme = theme;
    }

    public static int getSubTheme() {
        return subTheme;
    }

    public static void setSubTheme(int subTheme) {
        ProgressService.subTheme = subTheme;
    }

    public static int getTheory() {
        return theory;
    }

    public static void setTheory(int theory) {
        ProgressService.theory = theory;
    }

    public static int getPractice() {
        return practice;
    }

    public static void setPractice(int practice) {
        ProgressService.practice = practice;
    }
}
