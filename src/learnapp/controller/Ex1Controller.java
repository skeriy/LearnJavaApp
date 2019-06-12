package learnapp.controller;

import com.github.fxrouter.FXRouter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Ex1Controller {
    @FXML
    private Button backToMenuBtn;

    @FXML
    private VBox radioBtnBox;

    @FXML
    private RadioButton rad1;

    @FXML
    private RadioButton rad2;

    @FXML
    private RadioButton rad3;

    @FXML
    private RadioButton rad4;

    @FXML
    private VBox checkBtnBox;

    @FXML
    private CheckBox check1;

    @FXML
    private CheckBox check2;

    @FXML
    private CheckBox check3;

    @FXML
    private CheckBox check4;


    @FXML
    public void onBackToMenu() throws IOException {
        FXRouter.goTo("Menu");
    }

    @FXML
    public void onCheck() {
        System.out.println("In check radioAnswer: " + radioAnswer);
        checkCheckBoxes();
        System.out.println("In check CheckBoxAnswer: " + checkBoxAnswersToString());
    }

    private String radioAnswer;
    private ArrayList<Integer> checkBoxAnswers = new ArrayList<>();

    public void initialize() {
        initializeRadioButtons();
    }

    private void initializeRadioButtons() {
        ToggleGroup toggleGroup= new ToggleGroup();
        toggleGroup.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (toggleGroup.getSelectedToggle() != null) {
                RadioButton button = (RadioButton) toggleGroup.getSelectedToggle();
                radioAnswer = button.getText();
                System.out.println("Button: " + radioAnswer);
            }
        });
        rad1.setToggleGroup(toggleGroup);
        rad2.setToggleGroup(toggleGroup);
        rad3.setToggleGroup(toggleGroup);
        rad4.setToggleGroup(toggleGroup);

        rad1.setSelected(true);
    }

    private void checkCheckBoxes(){
        checkBoxAnswers.clear();

        if (check1.isSelected()){
            checkBoxAnswers.add(1);
        }
        if (check2.isSelected()){
            checkBoxAnswers.add(2);
        }
        if (check3.isSelected()){
            checkBoxAnswers.add(3);
        }
        if (check4.isSelected()){
            checkBoxAnswers.add(4);
        }
    }

    private String checkBoxAnswersToString(){
        StringBuilder result = new StringBuilder();
        for (Integer answer : checkBoxAnswers) {
            result.append(answer);
            result.append(" ");
        }
        return result.toString();
    }

}
