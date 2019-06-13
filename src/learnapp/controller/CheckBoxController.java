package learnapp.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fxrouter.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import learnapp.pojo.Practice;
import learnapp.service.DataService;
import learnapp.service.RouteService;
import learnapp.service.UtilService;

import java.io.IOException;
import java.util.ArrayList;

public class CheckBoxController {
    @FXML
    private Button backToMenuBtn;

    @FXML
    private VBox checkBtnBox;

    @FXML
    private Text checkBoxExText;

    @FXML
    private CheckBox check1;

    @FXML
    private CheckBox check2;

    @FXML
    private CheckBox check3;

    @FXML
    private CheckBox check4;

    private ArrayList<String> checkBoxAnswers = new ArrayList<>();
    private Practice practiceObject;

    @FXML
    public void onBackToMenu() throws IOException {
        FXRouter.goTo("Themes");
    }

    @FXML
    public void onCheck() {
        checkCheckBoxes();
        System.out.println("In check CheckBoxAnswer: " + checkBoxAnswersToString());
    }

    public void initialize() {
        JsonNode rootNode = DataService.getDataRootNode();
        JsonNode subThemeNode = rootNode.path("theme").path(RouteService.getTheme().toString()).path("sub_theme").path(RouteService.getSubTheme().toString());
        JsonNode practice = subThemeNode.path("practice").get(RouteService.getPractice().toString());

        practiceObject = UtilService.convertJsonNodeToPractice(practice);

        check1.setText(practiceObject.getQuestions().get(0));
        check2.setText(practiceObject.getQuestions().get(1));
        check3.setText(practiceObject.getQuestions().get(2));
        check4.setText(practiceObject.getQuestions().get(3));

        checkBoxExText.setText(practiceObject.getText());
    }

    private void checkCheckBoxes(){
        checkBoxAnswers.clear();

        if (check1.isSelected()){
            checkBoxAnswers.add(check1.getText());
        }
        if (check2.isSelected()){
            checkBoxAnswers.add(check2.getText());
        }
        if (check3.isSelected()){
            checkBoxAnswers.add(check3.getText());
        }
        if (check4.isSelected()){
            checkBoxAnswers.add(check4.getText());
        }

        boolean result = true;
        for (String answer : checkBoxAnswers) {
            if (practiceObject.getSucces().contains(answer)) {
                System.out.println("OK");
                continue;
            } else {
                System.out.println("NO");
                result = false;
                break;
            }
        }
        if (checkBoxAnswers.size() != practiceObject.getSucces().size()){
            result = false;
        }

        if (result) {
            System.out.println("GOOOOD");
        } else {
            System.out.println("NOT GOOOOD");
        }
    }

    private String checkBoxAnswersToString(){
        StringBuilder result = new StringBuilder();
        for (String answer : checkBoxAnswers) {
            result.append(answer);
            result.append(" ");
        }
        return result.toString();
    }
}
