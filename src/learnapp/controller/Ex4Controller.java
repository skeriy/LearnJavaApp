package learnapp.controller;

import com.github.fxrouter.FXRouter;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class Ex4Controller {
    @FXML
    private VBox codeVBox;

    @FXML
    private HBox answersHBox;

    @FXML
    private Button backToMenuBtn;

    @FXML
    private Button checkBtn;

    @FXML
    public void onBackToMenu() throws IOException {
        FXRouter.goTo("Menu");
    }

    @FXML
    public void onCheck() {
        String result = "";
        System.out.println("on check: " + result);
    }

    private Button answerBtn1;
    private Button answerBtn2;
    private HBox fieldHBox;

    public void initialize() {
        HBox row = new HBox();
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

        answerBtn1.setOnDragDetected(new EventHandler <MouseEvent>(){
            public void handle(MouseEvent event){
                dragDetected(event);
            }
        });

        answerBtn1.setOnDragDone(new EventHandler <DragEvent>(){
            public void handle(DragEvent event){
                dragDone(event);
            }
        });

        answerBtn2.setOnDragDetected(new EventHandler <MouseEvent>(){
            public void handle(MouseEvent event){
                dragDetected(event);
            }
        });

        answerBtn2.setOnDragDone(new EventHandler <DragEvent>(){
            public void handle(DragEvent event){
                dragDone(event);
            }
        });



        // Add mouse event handlers for the target
        fieldHBox.setOnDragOver(new EventHandler <DragEvent>(){
            public void handle(DragEvent event){
                dragOver(event);
            }
        });

        fieldHBox.setOnDragDropped(new EventHandler <DragEvent>(){
            public void handle(DragEvent event){
                dragDropped(event);
            }
        });

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
            sourceText =btn.getText() ;
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

        if (dragboard.hasString())
        {
            event.acceptTransferModes(TransferMode.COPY);
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
            fieldHBox.getChildren().clear();
            fieldHBox.getChildren().add(t);

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
