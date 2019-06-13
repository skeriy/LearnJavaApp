package learnapp.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fxrouter.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import learnapp.pojo.Progress;
import learnapp.service.DataService;
import learnapp.service.ProgressService;
import learnapp.service.RouteService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SubThemesController {

    @FXML
    private Button backToMenuBtn;

    @FXML
    private VBox subThemesVBox;

    @FXML
    public void onBackToMenu(){
        try {
            FXRouter.goTo("Themes");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void initialize() {
        initSubThemes();
    }

    private void initSubThemes() {
        JsonNode rootNode = DataService.getDataRootNode();
        JsonNode subThemeNode = rootNode.path("theme").path((String) FXRouter.getData()).path("sub_theme");

        Map<String, String> subThemesNames = new HashMap<>();
        Iterator<JsonNode> subThemesIt = subThemeNode.elements();

        while (subThemesIt.hasNext()) {
            JsonNode subTheme = subThemesIt.next();
            subThemesNames.put(subTheme.get("id").asText(), subTheme.get("name").asText());
        }

        ArrayList<Button> subThemesButtons = new ArrayList<>();
        for (Map.Entry<String, String> entry : subThemesNames.entrySet()) {
            Button button = new Button(entry.getValue());
            button.setPrefWidth(subThemesVBox.getPrefWidth());

            button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                try {
                    String subThemeId = entry.getKey();
                    RouteService.setSubTheme(Integer.valueOf(subThemeId));
                    calculateSubThemesTheoryAndPractice(subThemeId);

                    RouteService.setTheory(RouteService.getMinTheory());
                    RouteService.setPractice(RouteService.getMinPractice());
                    FXRouter.goTo("theory", entry.getKey());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            subThemesButtons.add(button);
        }
        for (Button button : subThemesButtons) {
            subThemesVBox.getChildren().add(button);
        }

    }

    private void calculateSubThemesTheoryAndPractice(String subThemeId) {
        int maxTheory = 0;
        int maxPractice = 0;

        Iterator<JsonNode> theory = DataService.getDataRootNode().path("theme").path((String) FXRouter.getData()).path("sub_theme").path(subThemeId).path("theory").elements();
        Iterator<JsonNode> practice = DataService.getDataRootNode().path("theme").path((String) FXRouter.getData()).path("sub_theme").path(subThemeId).path("practice").elements();

        while (theory.hasNext()) {
            theory.next();
            maxTheory++;
        }

        while (practice.hasNext()) {
            practice.next();
            maxPractice++;
        }
        RouteService.setMaxTheory(maxTheory);
        RouteService.setMaxPractice(maxPractice);
    }
}
