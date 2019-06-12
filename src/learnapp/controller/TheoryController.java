package learnapp.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fxrouter.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import learnapp.service.RouteService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TheoryController {

    @FXML
    private Button backToSubThemesBtn;

    @FXML
    private Button goToPracticeBtn;

    @FXML
    private FlowPane theoryFlowPane;

    @FXML
    private Text theoryText;

    @FXML
    public void onBackToSubThemes() throws IOException{
        FXRouter.goTo("SubThemes", RouteService.getTheme());
    }

    @FXML
    public void onGoToPractice() {

    }

    public void initialize() {
        initTheory();
    }

    public void initTheory() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get("data/data.json"));
            JsonNode rootNode = objectMapper.readTree(jsonData);
            JsonNode subThemeNode = rootNode.path("theme").path(RouteService.getTheme()).path("sub_theme").path(RouteService.getSubTheme());

            JsonNode theoryNode = subThemeNode.path("theory");

            theoryText.setText(theoryNode.get("1").asText());

            System.out.println();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
