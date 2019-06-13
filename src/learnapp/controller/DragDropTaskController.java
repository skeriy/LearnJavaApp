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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import learnapp.pojo.Practice;
import learnapp.service.DataService;
import learnapp.service.RouteService;
import learnapp.service.UtilService;

import java.io.IOException;
import java.util.ArrayList;

public class DragDropTaskController {
    @FXML
    private VBox codeVBox;

    @FXML
    private HBox answersHBox;

    @FXML
    private Button backToMenuBtn;

    @FXML
    private Button checkBtn;

    @FXML
    private Text dragDropExText;

    @FXML
    public void onBackToMenu() throws IOException {
        FXRouter.goTo("Themes");
    }

    @FXML
    public void onCheck() {
        boolean result = true;
        for (int i = 0; i < allCheckedFields.size(); i++) {
            Text text =(Text) allCheckedFields.get(i).getChildren().get(0);
            if (!text.getText().equals(practiceObject.getSucces().get(i))) {
                result = false;
            }
        }
        System.out.println("on check: " + result);
    }

    private Button answerBtn1;
    private Button answerBtn2;
    private HBox fieldHBox;
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

        dragDropExText.setText(practiceObject.getText());


       /* HBox row = new HBox();
        fieldHBox = new HBox();
        fieldHBox.setPrefWidth(100);
        fieldHBox.setStyle("-fx-background-color: #c1fcff");
        row.getChildren().addAll(new Text("Pre text "), fieldHBox, new Text(" post text."));
        codeVBox.getChildren().add(row);

        answerBtn1 = new Button();
        answerBtn1.setText("answer1");
        answerBtn1.setStyle("-fx-background-color: #fffc16");

        answerBtn2 = new Button();
        answerBtn2.setText("answer2");
        answerBtn2.setStyle("-fx-background-color: #01dcff");
        answersHBox.getChildren().addAll(answerBtn1, answerBtn2);

        // Add listeners for answers
        answerBtn1.setOnDragDetected(this::dragDetected);
        answerBtn1.setOnDragDone(this::dragDone);
        answerBtn2.setOnDragDetected(this::dragDetected);
        answerBtn2.setOnDragDone(this::dragDone);



        // Add mouse event handlers for the target
        fieldHBox.setOnDragOver(this::dragOver);
        fieldHBox.setOnDragDropped(this::dragDropped);*/

    }

    private void dragDetected(MouseEvent event)
    {
        // User can drag only when there is text in the source field
//        String sourceText = answerBtn1.getText();
        Object objectBtn = event.getSource();
        Button btn;
        String sourceText;
        if (objectBtn instanceof Button) {
            btn = (Button) objectBtn;
            sourceText = btn.getText() ;
        } else {
            System.out.println("Not button");
            event.consume();
            return;
        }

//        if (sourceText == null || sourceText.trim().equals(""))
//        {
//            event.consume();
//            return;
//        }

        // Initiate a drag-and-drop gesture
        Dragboard dragboard = btn.startDragAndDrop(TransferMode.COPY);

        // Add the source text to the Dragboard
        ClipboardContent content = new ClipboardContent();
        content.putString(sourceText);

        dragboard.setDragView(textToImage(sourceText));

        dragboard.setContent(content);
        event.consume();
    }

    private void dragOver(DragEvent event)
    {
        // If drag board has a string, let the event know that
        // the target accepts copy and move transfer modes
        Dragboard dragboard = event.getDragboard();
        System.out.println("over");
        if (dragboard.hasString())
        {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }

        event.consume();
    }

    private void dragDropped(DragEvent event)
    {
        // Transfer the data to the target
        Dragboard dragboard = event.getDragboard();

        if (dragboard.hasString())
        {
            System.out.println(dragboard.getString());
            Text t = new Text(dragboard.getString());
            HBox hBox = (HBox) event.getSource();
            hBox.getChildren().clear();
            hBox.getChildren().add(t);

            // Data transfer is successful
            event.setDropCompleted(true);
        }
        else
        {
            // Data transfer is not successful
            event.setDropCompleted(false);
        }

        event.consume();
    }

    private void dragDone(DragEvent event)
    {
        // Check how data was transfered to the target. If it was moved, clear the text in the source.
        TransferMode modeUsed = event.getTransferMode();

        if (modeUsed == TransferMode.MOVE)
        {
//            sourceFld.setText("");
        }

        event.consume();
    }

    private Image textToImage(String text) {
        Label label = new Label(text);
        label.setPadding(new Insets(0,0,0,20));
        label.setMinSize(125, 25);
        label.setMaxSize(125, 25);
        label.setPrefSize(125, 25);
        label.setStyle("-fx-background-color: green; -fx-text-fill:black;");
        label.setWrapText(true);
        Scene scene = new Scene(new Group(label));
        WritableImage img = new WritableImage(125, 25) ;
        scene.snapshot(img);
        return img ;
    }

}
