package learnapp.controller;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;

public class TaskController {

    @FXML
    private Button backButton;

    @FXML
    private AnchorPane taskAnchorRoot;

    public TaskController() {
    }

    @FXML
    public void onBackButton() throws IOException{
        System.out.println("backButton");

        Parent root = FXMLLoader.load(getClass().getResource("/learnapp/view/menu.fxml"));
        Scene scene = backButton.getScene();

        root.translateXProperty().set(scene.getWidth());

        StackPane parentContainer = (StackPane) scene.getRoot();
        parentContainer.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), keyValue);
        timeline.getKeyFrames().add(keyFrame);

        timeline.setOnFinished(event -> {
            parentContainer.getChildren().remove(taskAnchorRoot);
        });

        timeline.play();

    }
}
