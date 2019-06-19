package learnapp.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fxrouter.FXRouter;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import learnapp.service.DataService;
import learnapp.service.ProgressService;
import learnapp.service.RouteService;

import java.io.IOException;

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
    private Text themeText;

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
        JsonNode subThemeNode = rootNode.path("theme").path(RouteService.getTheme().toString()).path("sub_theme");

        themeText.setText(rootNode.path("theme").path(RouteService.getTheme().toString()).path("name").asText());

        Map<String, String> subThemesNames = new HashMap<>();
        Iterator<JsonNode> subThemesIt = subThemeNode.elements();

        int lastSubTheme = 0;
        while (subThemesIt.hasNext()) {
            JsonNode subTheme = subThemesIt.next();
            subThemesNames.put(subTheme.get("id").asText(), subTheme.get("name").asText());
            lastSubTheme++;
        }
        ProgressService.setLastSuccessSubTheme(lastSubTheme);

        ArrayList<Button> subThemesButtons = new ArrayList<>();
        for (Map.Entry<String, String> entry : subThemesNames.entrySet()) {
            String subThemeId = entry.getKey();

            Button button = new Button(entry.getValue());
            button.setPrefWidth(subThemesVBox.getPrefWidth());
            button.setStyle("-fx-background-color: rgba(102, 218, 255, 0.85); -fx-font-size: 16");

            if (ProgressService.getTheme() == RouteService.getTheme() && Integer.valueOf(subThemeId) > ProgressService.getSubTheme()){
                button.setDisable(true);
            }

            button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                try {

                    RouteService.setSubTheme(Integer.valueOf(subThemeId));
                    calculateSubThemesMaxTheoryAndPractice(subThemeId);

                    RouteService.setTheory(RouteService.getMinTheory());
                    RouteService.setPractice(RouteService.getMinPractice());

                    FXRouter.goTo("theory", subThemeId);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            subThemesButtons.add(button);
        }
        for (Button button : subThemesButtons) {
            subThemesVBox.getChildren().add(button);
        }

        subThemesVBox.setSpacing(5);

    }

    private void calculateSubThemesMaxTheoryAndPractice(String subThemeId) {
        int maxTheory = 0;
        int maxPractice = 0;


        Iterator<JsonNode> theory = DataService.getDataRootNode().path("theme").path(RouteService.getTheme().toString()).path("sub_theme").path(subThemeId).path("theory").elements();
        Iterator<JsonNode> practice = DataService.getDataRootNode().path("theme").path(RouteService.getTheme().toString()).path("sub_theme").path(subThemeId).path("practice").elements();

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
