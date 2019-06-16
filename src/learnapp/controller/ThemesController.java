package learnapp.controller;

import com.github.fxrouter.FXRouter;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import learnapp.service.DataService;
import learnapp.service.LoginService;
import learnapp.service.ProgressService;
import learnapp.service.RouteService;

import java.io.IOException;

public class ThemesController {

    @FXML
    private AnchorPane anchorRoot;

    @FXML
    private Button progressToDefaultBtn;

    @FXML
    private VBox themesVBox;

    @FXML
    private Text loginNameText;

    @FXML
    private Button userSettingsBtn;

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

    @FXML
    public void setProgressToDefault() {
        ProgressService.setProgressToDefault();
    }

    @FXML
    public void onUserSettings() throws IOException{
        FXRouter.goTo("UserSettings");
    }

    @FXML
    public void onTheme1() throws IOException {
        RouteService.setTheme(1);
        FXRouter.goTo("SubThemes");
    }
    @FXML
    public void onTheme2() throws IOException {
        RouteService.setTheme(2);
        FXRouter.goTo("SubThemes");
    }
    @FXML
    public void onTheme3() throws IOException {
        RouteService.setTheme(3);
        FXRouter.goTo("SubThemes");
    }
    @FXML
    public void onTheme4() throws IOException {
        RouteService.setTheme(4);
        FXRouter.goTo("SubThemes");
    }
    @FXML
    public void onTheme5() throws IOException {
        RouteService.setTheme(5);
        FXRouter.goTo("SubThemes");
    }
    @FXML
    public void onTheme6() throws IOException {
        RouteService.setTheme(6);
        FXRouter.goTo("SubThemes");
    }
    @FXML
    public void onTheme7() throws IOException {
        RouteService.setTheme(7);
        FXRouter.goTo("SubThemes");
    }

    public void initialize() {
        LoginService.setState("RunProgram");
        loginNameText.setText(LoginService.getLogin());

        if (LoginService.isAdminMode()) {
            userSettingsBtn.setVisible(true);
        }

        Stage stage = DataService.getMainStage();
        stage.setWidth(800);
        stage.setHeight(600);

        ProgressService.init();
        ObservableList<Node> buttons = themesVBox.getChildren();
        for (int i = 1; i <= buttons.size(); i++) {
            if (i > ProgressService.getTheme()) {
                buttons.get(i - 1).setDisable(true);
            }
        }
    }

}
