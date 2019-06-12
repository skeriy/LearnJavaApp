package learnapp;

import com.github.fxrouter.FXRouter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import learnapp.service.ProgressService;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        FXRouter.bind(this, stage, "JavaLearn", 800, 600);
        FXRouter.setAnimationType("fade", 500);

        FXRouter.when("Themes", "view/themes.fxml");
        FXRouter.when("SubThemes", "view/subThemes.fxml");
        FXRouter.when("Theory", "view/theory.fxml");
        FXRouter.when("EX1", "view/ex1.fxml");
        FXRouter.when("EX2", "view/ex2.fxml");
        FXRouter.when("EX3", "view/ex3.fxml");
        FXRouter.when("EX4", "view/ex4.fxml");
        FXRouter.when("EX5", "view/ex5.fxml");

        ProgressService.init();

        FXRouter.goTo("Themes");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
