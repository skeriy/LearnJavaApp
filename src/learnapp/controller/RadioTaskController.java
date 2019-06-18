package learnapp.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fxrouter.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import learnapp.pojo.Practice;
import learnapp.service.DataService;
import learnapp.service.ProgressService;
import learnapp.service.RouteService;
import learnapp.service.UtilService;

import java.io.IOException;


public class RadioTaskController {
    @FXML
    private AnchorPane practiceAnchorPane;

    @FXML
    private HBox navigateHBox;

    @FXML
    private Button backToMenuBtn;

    @FXML
    private VBox radioBtnBox;

    @FXML
    private TextFlow radioExText;

    @FXML
    private RadioButton rad1;

    @FXML
    private RadioButton rad2;

    @FXML
    private RadioButton rad3;

    @FXML
    private RadioButton rad4;


    @FXML
    public void onBackToMenu() throws IOException {
        FXRouter.goTo("SubThemes");
    }

    @FXML
    public void onCheck() {
        checkRadioAnswer();
    }

    private String radioAnswer;
    private String succesAnswer;

    public void initialize() {
        initializeRadioButtons();
        UtilService.showNavigatePanel(navigateHBox);
    }

    private void initializeRadioButtons() {
        JsonNode rootNode = DataService.getDataRootNode();
        JsonNode subThemeNode = rootNode.path("theme").path(RouteService.getTheme().toString()).path("sub_theme").path(RouteService.getSubTheme().toString());
        JsonNode practice = subThemeNode.path("practice").get(RouteService.getPractice().toString());

        Practice practiceObject = UtilService.convertJsonNodeToPractice(practice);

        succesAnswer = practiceObject.getSucces().get(0);

        ToggleGroup toggleGroup= new ToggleGroup();
        toggleGroup.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (toggleGroup.getSelectedToggle() != null) {
                RadioButton button = (RadioButton) toggleGroup.getSelectedToggle();
                radioAnswer = button.getText();
            }
        });

        rad1.setToggleGroup(toggleGroup);
        rad2.setToggleGroup(toggleGroup);
        rad3.setToggleGroup(toggleGroup);
        rad4.setToggleGroup(toggleGroup);

        rad1.setText(practiceObject.getQuestions().get(0));
        rad2.setText(practiceObject.getQuestions().get(1));
        rad3.setText(practiceObject.getQuestions().get(2));
        rad4.setText(practiceObject.getQuestions().get(3));

        rad1.setSelected(true);

        radioExText.getChildren().add(new Text(practiceObject.getText()));


    }

    private void checkRadioAnswer() {
        boolean result = radioAnswer.equals(succesAnswer);
        ProgressService.updateProgress(result);

    }

}
