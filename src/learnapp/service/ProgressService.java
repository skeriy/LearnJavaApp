package learnapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fxrouter.FXRouter;

import javax.xml.crypto.Data;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ProgressService {
    private static int theme;
    private static int subTheme;
    private static int theory;
    private static int practice;
    private static int lastSuccessSubTheme;
    private static String currentState;

    public static void init() {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get("data/progress.json"));
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonData);
            DataService.setProgressData(rootNode);

            setTheme(rootNode.get("theme").asInt());
            setSubTheme(rootNode.get("sub_theme").asInt());
            setTheory(rootNode.get("theory").asInt());
            setPractice(rootNode.get("practice").asInt());
            setCurrentState(rootNode.get("current").asText());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static void setProgressToDefault() {
        setTheme(1);
        setSubTheme(1);
        setTheory(1);
        setPractice(1);
        try {
            FXRouter.goTo("Themes");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateProgress(boolean resultOfCheckPractice) {
        try {
            if (resultOfCheckPractice) {
                System.out.println("OK");
                if (RouteService.getTheory().equals(RouteService.getMaxTheory())) {
                    System.out.println("THEME " + RouteService.getTheme() + " SUBTHEME " + RouteService.getSubTheme() + " DONE! GO ALL SUBTHEMES!");
                    if (ProgressService.getSubTheme() == RouteService.getSubTheme()){
                        System.out.println("\n\nINCREMENTED ");
                        ProgressService.incSubTheme();
                    }
                    if (ProgressService.getLastSuccessSubTheme() == RouteService.getSubTheme()) {
                        ProgressService.incTheme();
                        ProgressService.setSubTheme(1);
                        FXRouter.goTo("Themes");
                    } else {
                        FXRouter.goTo("SubThemes");
                    }
                } else {
                    RouteService.incPractice();
                    RouteService.incTheory();
                    ProgressService.incTheory();
                    ProgressService.incPractice();
                    FXRouter.goTo("theory");
                }
            } else {
                System.out.println("NO");
                FXRouter.goTo("theory");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void saveProgressToFile() {
        JsonNode savedProgress = DataService.getProgressData();
        ((ObjectNode) savedProgress).put("theme", ProgressService.getTheme());
        ((ObjectNode) savedProgress).put("sub_theme", ProgressService.getSubTheme());
        ((ObjectNode) savedProgress).put("theory", ProgressService.getTheory());
        ((ObjectNode) savedProgress).put("practice", ProgressService.getPractice());

        ObjectMapper mapper = new ObjectMapper();
        String s = "";
        try {
             s = mapper.writeValueAsString(savedProgress);
            FileWriter writer = new FileWriter("data/progress.json", false);
            writer.write(s);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(s);
    }

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

    public static int getLastSuccessSubTheme() {
        return lastSuccessSubTheme;
    }

    public static void setLastSuccessSubTheme(int lastSuccessSubTheme) {
        ProgressService.lastSuccessSubTheme = lastSuccessSubTheme;
    }
}
