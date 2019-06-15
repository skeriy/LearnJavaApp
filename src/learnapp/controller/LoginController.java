package learnapp.controller;

import com.github.fxrouter.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import learnapp.service.DataService;
import learnapp.service.LoginService;

import java.io.IOException;


public class LoginController {

    @FXML
    private Text loginTitleText;

    @FXML
    private Hyperlink loginLink;

    @FXML
    private TextField loginTextField;

    @FXML
    private Button loginBtn;

    @FXML
    public void onLoginBtn() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (loginTextField.getText().isEmpty()) {
            alert.setTitle("Ошибка логина");
            alert.setHeaderText(null);
            alert.setContentText("Введите логин");
            alert.showAndWait();
            System.out.println("");
        } else {
            String login = loginTextField.getText();
            LoginService.setLogin(login);

            if (LoginService.getState().equals("login")) {
                if (login.toLowerCase().equals("admin")){
                    LoginService.setAdminMode(true);
                }
                try {
                    FXRouter.goTo("Themes");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                if (login.toLowerCase().equals("admin") || LoginService.getSavedUsers().contains(login)) {
                    alert.setTitle("Ошибка создания учетной записи");
                    alert.setHeaderText(null);
                    alert.setContentText("Имя существует");
                    alert.showAndWait();
                    System.out.println("Name exist");
                    loginTextField.setText("");
                } else {
                    LoginService.addNewUser();
                    LoginService.setState("login");
                    try {
                        FXRouter.goTo("Themes");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }

    @FXML
    public void onLoginLink() {
        try {
            if (LoginService.getState().equals("login")) {
                LoginService.setState("register");
                FXRouter.goTo("Login");
            } else {
                LoginService.setState("login");
                FXRouter.goTo("Login");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void initialize() {
        if (LoginService.getState().equals("login")) {
            loginTitleText.setText("Авторизация");
            loginLink.setText("Создать логин");
            loginBtn.setText("Войти");
        } else {
            loginTitleText.setText("Создание учетной записи");
            loginLink.setText("Войти в приложение");
            loginBtn.setText("Создать");
        }
    }
}
