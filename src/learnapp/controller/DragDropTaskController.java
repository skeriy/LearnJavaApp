package learnapp.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fxrouter.FXRouter;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
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
import java.util.ArrayList;

public class DragDropTaskController {
    @FXML
    private AnchorPane practiceAnchorPane;

    @FXML
    private HBox navigateHBox;

    @FXML
    private VBox codeVBox;

    @FXML
    private HBox answersHBox;

    @FXML
    private Button backToMenuBtn;

    @FXML
    private Button checkBtn;

    @FXML
    private TextFlow dragDropExText;

    @FXML
    public void onBackToMenu() throws IOException {
        FXRouter.goTo("SubThemes");
    }

    @FXML
    public void onCheck() {
        checkFields();
    }

    private Practice practiceObject;
    private ArrayList<HBox> allCheckedFields = new ArrayList<>();

    public void initialize() {
        JsonNode rootNode = DataService.getDataRootNode();
        JsonNode subThemeNode = rootNode.path("theme").path(RouteService.getTheme().toString()).path("sub_theme").path(RouteService.getSubTheme().toString());
        JsonNode practice = subThemeNode.path("practice").get(RouteService.getPractice().toString());

        practiceObject = UtilService.convertJsonNodeToPractice(practice);

        int rowCount = practiceObject.getRowsMap().size();
        int answersCount = practiceObject.getQuestions().size();

        for (int i = 1; i <= rowCount; i++) {
            HBox row = new HBox();
            if (practiceObject.getPasteElements().contains(i)) {
                HBox checkField = new HBox();
                checkField.setPrefHeight(20);
                checkField.setPrefWidth(70);
                checkField.setStyle("-fx-background-color: #e7f5ff");

                checkField.setOnDragOver(this::dragOver);
                checkField.setOnDragDropped(this::dragDropped);

                allCheckedFields.add(checkField);
                ArrayList<String> subStrings = practiceObject.getRowsMap().get(i);
                row.getChildren().addAll(new Text(subStrings.get(0)), checkField, new Text(subStrings.get(1)));
            } else {
                row.getChildren().add(new Text(practiceObject.getRowsMap().get(i).get(0)));
            }
            codeVBox.getChildren().add(row);
        }

        for (int i = 0; i < answersCount; i++) {
            Button answerBtn = new Button(practiceObject.getQuestions().get(i));
            answerBtn.setOnDragDetected(this::dragDetected);
            answerBtn.setOnDragDone(this::dragDone);
            answersHBox.getChildren().add(answerBtn);
        }

        dragDropExText.getChildren().add(new Text(practiceObject.getText()));

        UtilService.showNavigatePanel(navigateHBox);

    }

    private void dragDetected(MouseEvent event)
    {

        Object objectBtn = event.getSource();
        Button btn;
        String sourceText;
        if (objectBtn instanceof Button) {
            btn = (Button) objectBtn;
            sourceText = btn.getText() ;
        } else {
            event.consume();
            return;
        }

        Dragboard dragboard = btn.startDragAndDrop(TransferMode.COPY);


        ClipboardContent content = new ClipboardContent();
        content.putString(sourceText);

        dragboard.setDragView(UtilService.textToImage(sourceText));

        dragboard.setContent(content);
        event.consume();
    }

    private void dragOver(DragEvent event)
    {
        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasString())
        {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }

        event.consume();
    }

    private void dragDropped(DragEvent event)
    {
        Dragboard dragboard = event.getDragboard();

        if (dragboard.hasString())
        {
            Text t = new Text(dragboard.getString());
            HBox hBox = (HBox) event.getSource();
            hBox.getChildren().clear();
            hBox.getChildren().add(t);

            event.setDropCompleted(true);
        }
        else
        {
            event.setDropCompleted(false);
        }

        event.consume();
    }

    private void dragDone(DragEvent event)
    {
        TransferMode modeUsed = event.getTransferMode();

        event.consume();
    }

    private void checkFields() {
        boolean result = true;
        for (int i = 0; i < allCheckedFields.size(); i++) {
            Text text =(Text) allCheckedFields.get(i).getChildren().get(0);
            if (!text.getText().equals(practiceObject.getSucces().get(i))) {
                result = false;
            }
        }

        ProgressService.updateProgress(result);
    }

}
