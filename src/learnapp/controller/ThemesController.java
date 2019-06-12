package learnapp.controller;

import com.github.fxrouter.FXRouter;
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

public class ThemesController {

    @FXML
    private AnchorPane anchorRoot;

    @FXML
    private Button Theme1Btn;
    @FXML
    private Button Theme2Btn;
    @FXML
    private Button Theme3Btn;
    @FXML
    private Button Theme4Btn;
    @FXML
    private Button Theme5Btn;
    @FXML
    private Button Theme6Btn;
    @FXML
    private Button Theme7Btn;

    public ThemesController() {

    }

    @FXML
    public void onTheme1() throws IOException {
        FXRouter.goTo("SubThemes", "1");
    }
    @FXML
    public void onTheme2() throws IOException {
        FXRouter.goTo("SubThemes", "2");
    }
    @FXML
    public void onTheme3() throws IOException {
        FXRouter.goTo("SubThemes", "3");
    }
    @FXML
    public void onTheme4() throws IOException {
        FXRouter.goTo("SubThemes", "4");
    }
    @FXML
    public void onTheme5() throws IOException {
        FXRouter.goTo("SubThemes", "5");
    }
    @FXML
    public void onTheme6() throws IOException {
        FXRouter.goTo("SubThemes", "6");
    }
    @FXML
    public void onTheme7() throws IOException {
        FXRouter.goTo("SubThemes", "7");
    }

}
