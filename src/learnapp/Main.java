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
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> ProgressService.saveProgressToFile());

        FXRouter.bind(this, stage, "JavaLearn", 800, 600);
        FXRouter.setAnimationType("fade", 500);

        FXRouter.when("Themes", "view/themes.fxml");
        FXRouter.when("SubThemes", "view/subThemes.fxml");
        FXRouter.when("theory", "view/theory.fxml");
        FXRouter.when("RadioTask", "view/radioTask.fxml");
        FXRouter.when("CheckBoxTask", "view/checkBoxTask.fxml");
        FXRouter.when("ProgramListTask", "view/programListTask.fxml");
        FXRouter.when("InputTextTask", "view/inputText.fxml");
        FXRouter.when("DragDropTask", "view/dragDropTask.fxml");

        DataService.setDataRootNode();
        ProgressService.init();

        FXRouter.goTo("Themes");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
