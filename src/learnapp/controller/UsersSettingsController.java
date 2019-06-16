package learnapp.controller;

import com.github.fxrouter.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import learnapp.pojo.User;
import learnapp.service.UserService;

import java.io.IOException;

public class UsersSettingsController {

    @FXML
    private Button backToMenuBtn;

    @FXML
    private TableColumn<User, String> userNameColumn;

    @FXML
    private TableColumn<User, String> themeColumn;

    @FXML
    private TableColumn<User, String> subThemeColumn;

    @FXML
    private TableColumn<User, String> theoryColumn;

    @FXML
    private TableColumn<User, String> practiceColumn;

    @FXML
    private TableColumn<User, Button> buttonColumn;


    @FXML
    private TableView<User> usersTable;


    @FXML
    public void onBackToMenu() throws IOException {
        FXRouter.goTo("Themes");
    }

    public void initialize() {
        usersTable.setItems(UserService.getAllUsers());
        userNameColumn.setCellValueFactory(cell -> cell.getValue().nameProperty());
        themeColumn.setCellValueFactory(cell -> cell.getValue().userThemeProperty());
        subThemeColumn.setCellValueFactory(cell -> cell.getValue().userSubThemeProperty());
        theoryColumn.setCellValueFactory(cell -> cell.getValue().userTheoryProperty());
        practiceColumn.setCellValueFactory(cell -> cell.getValue().userPracticeProperty());
        buttonColumn.setCellValueFactory(cell -> cell.getValue().userButtonProperty());
    }

}
