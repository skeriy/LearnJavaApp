package learnapp.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fxrouter.FXRouter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import learnapp.pojo.Practice;
import learnapp.service.DataService;
import learnapp.service.RouteService;
import learnapp.service.UtilService;

import java.io.IOException;
import java.util.ArrayList;

public class InputTextController {
    @FXML
    private AnchorPane practiceAnchorPane;

    @FXML
    private VBox codeVBox;

    @FXML
    private Button backToMenuBtn;

    @FXML
    private Button checkBtn;

    @FXML
    private Text inputTextExText;

    @FXML
    public void onBackToMenu() throws IOException {
        FXRouter.goTo("Menu");
    }

    @FXML
    public void onCheck() {
        boolean result = true;
        for (int i = 0; i < allTextFields.size(); i++) {
            if (!allTextFields.get(i).getText().equals(practiceObject.getSucces().get(i))) {
                result = false;
            }
        }
        System.out.println("on check: " + result);

        try {
            if (result) {
                System.out.println("OK");
                if (RouteService.getTheory().equals(RouteService.getMaxTheory())) {
                    System.out.println("SUBTHEME " + RouteService.getSubTheme() + " DONE! GO ALL SUBTHEMES!");
                    FXRouter.goTo("SubThemes");
                } else {
                    RouteService.incPractice();
                    RouteService.incTheory();
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

    private ObservableList<TextField> allTextFields = FXCollections.observableArrayList();
    private Practice practiceObject;

    public void initialize() {
        JsonNode rootNode = DataService.getDataRootNode();
        JsonNode subThemeNode = rootNode.path("theme").path(RouteService.getTheme().toString()).path("sub_theme").path(RouteService.getSubTheme().toString());
        JsonNode practice = subThemeNode.path("practice").get(RouteService.getPractice().toString());

        practiceObject = UtilService.convertJsonNodeToPractice(practice);

        int rowCount = practiceObject.getRowsMap().size();

        for (int i = 1; i <= rowCount; i++) {
            HBox row = new HBox();
            if (practiceObject.getPasteElements().contains(i)) {
                TextField textField = new TextField();
                textField.setPrefWidth(50);
                allTextFields.add(textField);
                ArrayList<String> subStrings = practiceObject.getRowsMap().get(i);
                row.getChildren().addAll(new Text(subStrings.get(0)), textField, new Text(subStrings.get(1)));
            } else {
                row.getChildren().add(new Text(practiceObject.getRowsMap().get(rowCount).get(0)));
            }
            codeVBox.getChildren().add(row);
        }

        inputTextExText.setText(practiceObject.getText());

        UtilService.showNavigatePanel(practiceAnchorPane);
    }
}
