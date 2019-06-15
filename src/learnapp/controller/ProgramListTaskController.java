package learnapp.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fxrouter.FXRouter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import learnapp.pojo.Practice;
import learnapp.service.DataService;
import learnapp.service.ProgressService;
import learnapp.service.RouteService;
import learnapp.service.UtilService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProgramListTaskController {
    @FXML
    private AnchorPane practiceAnchorPane;

    @FXML
    private VBox draggableVbox;

    @FXML
    private Button backToMenuBtn;

    @FXML
    private Button checkBtn;

    @FXML
    private Text programListExText;

    @FXML
    public void onBackToMenu() throws IOException {
        FXRouter.goTo("Themes");
    }

    @FXML
    public void onCheck() {
        boolean result = true;
        for (int i = 0; i < answersObsList.size(); i++) {
            String answer = answersObsList.get(i);
            if (!answer.equals(practiceObject.getSucces().get(i))) {
                result = false;
            }
        }
        System.out.println("on check: " + result);

        ProgressService.updateProgress(result);
    }

    private ObservableList<String> answersObsList = FXCollections.observableArrayList();
    private Practice practiceObject;

    public void initialize() {

        JsonNode rootNode = DataService.getDataRootNode();
        JsonNode subThemeNode = rootNode.path("theme").path(RouteService.getTheme().toString()).path("sub_theme").path(RouteService.getSubTheme().toString());
        JsonNode practice = subThemeNode.path("practice").get(RouteService.getPractice().toString());

        practiceObject = UtilService.convertJsonNodeToPractice(practice);

        programListExText.setText(practiceObject.getText());

        answersObsList.addAll(practiceObject.getQuestions());

        ListView<String> listView = new ListView<>(answersObsList);
        listView.setCellFactory(param -> new AnswerCell());
        listView.setPrefWidth(180);

        draggableVbox.getChildren().add(listView);

        UtilService.showNavigatePanel(practiceAnchorPane);
    }



    private class AnswerCell extends ListCell<String> {
        private final Text text = new Text();

        public AnswerCell() {
            ListCell<String> thisCell = this;

            setOnDragDetected(event -> {
                if (getItem() == null) {
                    return;
                }

                ObservableList<String> items = getListView().getItems();

                Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(getItem());
                dragboard.setDragView(textToImage(answersObsList.get(items.indexOf(getItem()))));
                dragboard.setContent(content);

                event.consume();
            });

            setOnDragOver(event -> {
                if (event.getGestureSource() != thisCell &&
                        event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }

                event.consume();
            });

            setOnDragEntered(event -> {
                if (event.getGestureSource() != thisCell &&
                        event.getDragboard().hasString()) {
                    setOpacity(0.3);
                }
            });

            setOnDragExited(event -> {
                if (event.getGestureSource() != thisCell &&
                        event.getDragboard().hasString()) {
                    setOpacity(1);
                }
            });

            setOnDragDropped(event -> {
                if (getItem() == null) {
                    return;
                }

                Dragboard db = event.getDragboard();
                boolean success = false;

                if (db.hasString()) {
                    ObservableList<String> items = getListView().getItems();
                    int draggedIdx = items.indexOf(db.getString());
                    int thisIdx = items.indexOf(getItem());

                    /*Image temp = answersObsList.get(draggedIdx);
                    answersObsList.set(draggedIdx, answersObsList.get(thisIdx));
                    answersObsList.set(thisIdx, temp);*/

                    items.set(draggedIdx, getItem());
                    items.set(thisIdx, db.getString());

                    List<String> itemscopy = new ArrayList<>(getListView().getItems());
                    getListView().getItems().setAll(itemscopy);

                    success = true;
                }
                event.setDropCompleted(success);

                event.consume();
            });

            setOnDragDone(DragEvent::consume);
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                System.out.println("empty");
            } else {
                text.setText(item);
                setGraphic(text);
                System.out.println(item);
                System.out.println("update");
            }
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
}


