package learnapp.controller;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class MenuController {

    @FXML
    private StackPane stackParentContainer;

    @FXML
    private AnchorPane anchorRoot;

    @FXML
    private Button sectionButton;

    public MenuController() {

    }

    @FXML
    public void onSelectSectionButton() throws IOException{
        System.out.println("In button");

        Parent root = FXMLLoader.load(getClass().getResource("/learnapp/view/task.fxml"));
        Scene scene = sectionButton.getScene();

        root.translateYProperty().set(scene.getHeight());

        stackParentContainer.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), keyValue);
        timeline.getKeyFrames().add(keyFrame);

        timeline.setOnFinished(event -> {
            stackParentContainer.getChildren().remove(anchorRoot);
        });

        timeline.play();
    }

}
