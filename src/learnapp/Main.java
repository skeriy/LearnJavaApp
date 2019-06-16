package learnapp;

import com.github.fxrouter.FXRouter;
import javafx.application.Application;
import javafx.stage.Stage;
import learnapp.service.DataService;
import learnapp.service.ProgressService;
import learnapp.service.RouteService;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        DataService.setMainStage(stage);
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> ProgressService.saveProgressToFile());

        FXRouter.bind(this, stage, "JavaLearn", 400, 200);
        FXRouter.setAnimationType("fade", 500);

        FXRouter.when("LoginOrRegister", "view/login.fxml");
        FXRouter.when("UserSettings", "view/usersSettings.fxml");
        FXRouter.when("Themes", "view/themes.fxml");
        FXRouter.when("SubThemes", "view/subThemes.fxml");
        FXRouter.when("theory", "view/theory.fxml");
        FXRouter.when("RadioTask", "view/radioTask.fxml");
        FXRouter.when("CheckBoxTask", "view/checkBoxTask.fxml");
        FXRouter.when("ProgramListTask", "view/programListTask.fxml");
        FXRouter.when("InputTextTask", "view/inputText.fxml");
        FXRouter.when("DragDropTask", "view/dragDropTask.fxml");

        ProgressService.initRootProgress();
        DataService.setDataRootNode();
        FXRouter.goTo("LoginOrRegister");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
