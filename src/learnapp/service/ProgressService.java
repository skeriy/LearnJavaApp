package learnapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fxrouter.FXRouter;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.stage.Screen;

import javax.xml.crypto.Data;
import java.io.*;
import java.nio.charset.StandardCharsets;
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
        JsonNode rootNode = DataService.getRootProgress();
        DataService.setProgressData(rootNode.path(LoginService.getLogin()));

        JsonNode userNode = DataService.getProgressData();
        setTheme(userNode.get("theme").asInt());
        setSubTheme(userNode.get("sub_theme").asInt());
        setTheory(userNode.get("theory").asInt());
        setPractice(userNode.get("practice").asInt());
    }

    public static void initRootProgress() {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get("data/progress.json"));
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonData);
            DataService.setRootProgress(rootNode);
        } catch (IOException e) {
            e.printStackTrace();
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
                if (RouteService.getTheory().equals(RouteService.getMaxTheory())) {
                    if (ProgressService.getSubTheme() == RouteService.getSubTheme()){
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
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Неправильный ответ");
                alert.setHeaderText(null);
                alert.setContentText("Задание решено неправильно. Поробуйте еще раз.");

                alert.showAndWait();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void saveProgressToFile() {
        if (!LoginService.getLogin().isEmpty() && LoginService.getState().equals("RunProgram")) {
            JsonNode savedProgress = DataService.getRootProgress().path(LoginService.getLogin());
            ((ObjectNode) savedProgress).put("theme", ProgressService.getTheme());
            ((ObjectNode) savedProgress).put("sub_theme", ProgressService.getSubTheme());
            ((ObjectNode) savedProgress).put("theory", ProgressService.getTheory());
            ((ObjectNode) savedProgress).put("practice", ProgressService.getPractice());

            ObjectMapper mapper = new ObjectMapper();
            String s = "";
            try {
                s = mapper.writeValueAsString(DataService.getRootProgress());
                Writer out = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream("data/progress.json"), StandardCharsets.UTF_8));
                out.write(s);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteUser(String name) {
        JsonNode usersProgress = DataService.getRootProgress();
        ((ObjectNode) usersProgress).remove(name);
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
