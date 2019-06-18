package learnapp.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fxrouter.FXRouter;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import learnapp.service.DataService;
import learnapp.service.ProgressService;
import learnapp.service.RouteService;
import learnapp.service.UtilService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TheoryController {

    @FXML
    private AnchorPane theoryAnchorPane;

    @FXML
    private Button backToSubThemesBtn;

    @FXML
    private Button goToPracticeBtn;

    @FXML
    private FlowPane theoryFlowPane;

    @FXML
    private Text theoryNameText;

    @FXML
    private TextFlow theoryText;

    @FXML
    private HBox navigateHBox;

    @FXML
    public void onBackToSubThemes() throws IOException{
        FXRouter.goTo("SubThemes", RouteService.getTheme().toString());
    }

    @FXML
    public void onGoToPractice() {
        try {
            JsonNode rootNode = DataService.getDataRootNode();
            JsonNode subThemeNode = rootNode.path("theme").path(RouteService.getTheme().toString()).path("sub_theme").path(RouteService.getSubTheme().toString());
            JsonNode practice = subThemeNode.path("practice").get(RouteService.getTheory().toString());

            switch (practice.get("type").asText()){
                case DataService.RADIO_TASK:
                    FXRouter.goTo("RadioTask");
                    break;
                case DataService.CHECKBOX_TASK:
                    FXRouter.goTo("CheckBoxTask");
                    break;
                case DataService.INPUT_TEXT_TASK:
                    FXRouter.goTo("InputTextTask");
                    break;
                case DataService.DRAG_DROP_TASK:
                    FXRouter.goTo("DragDropTask");
                    break;
                case DataService.PROGRAM_LIST_TASK:
                    FXRouter.goTo("ProgramListTask");
                    break;
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void initialize() {
        initTheory();
    }

    private void initTheory() {
        JsonNode rootNode = DataService.getDataRootNode();
        JsonNode subThemeNode = rootNode.path("theme").path(RouteService.getTheme().toString()).path("sub_theme").path(RouteService.getSubTheme().toString());

        JsonNode theoryNode = subThemeNode.path("theory");
        String s = RouteService.getTheory().toString();
        JsonNode n = theoryNode.get(s);
        theoryNameText.setText(theoryNode.get(RouteService.getTheory().toString()).get("name").asText());
        theoryText.getChildren().add(new Text(theoryNode.get(RouteService.getTheory().toString()).get("text").asText()));

        UtilService.showNavigatePanel(navigateHBox);

    }
}
