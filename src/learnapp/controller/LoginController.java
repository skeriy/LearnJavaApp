package learnapp.controller;

import com.github.fxrouter.FXRouter;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
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
        onLoginAction();
    }

    @FXML
    public void onLoginLink() {
        try {
            if (LoginService.getState().equals("login")) {
                LoginService.setState("register");
                FXRouter.goTo("LoginOrRegister");
            } else {
                LoginService.setState("login");
                FXRouter.goTo("LoginOrRegister");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void initialize() {
        if (LoginService.getState().equals("login")) {
            loginTitleText.setText("Авторизация");
            loginLink.setText("Создать нового пользователя");
            loginBtn.setText("Войти");
        } else {
            loginTitleText.setText("Регистрация");
            loginLink.setText("Войти");
            loginBtn.setText("Создать");
        }

        loginTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    onLoginAction();
                }
            }
        });
    }

    private void onLoginAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (loginTextField.getText().isEmpty()) {
            alert.setTitle("Ошибка авторизации");
            alert.setHeaderText(null);
            alert.setContentText("Введите логин");
            alert.showAndWait();
        } else {
            String login = loginTextField.getText();
            LoginService.setLogin(login);

            if (LoginService.getState().equals("login")) {
                if (login.toLowerCase().equals("admin")){
                    login = login.toLowerCase();
                    LoginService.setLogin(login.toLowerCase());
                    LoginService.setAdminMode(true);
                }
                if (LoginService.isUserExist(login)) {
                    try {
                        setWindowToCenter();
                        FXRouter.goTo("Themes");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    alert.setTitle("Ошибка авторизации");
                    alert.setHeaderText(null);
                    alert.setContentText("Пользователя '" + login + "' не существует. Вы можете его создать нажав на \"Создать нового пользователя\"");
                    alert.showAndWait();
                }
            } else {
                if (login.toLowerCase().equals("admin") || LoginService.isUserExist(login)) {
                    alert.setTitle("Ошибка создания учетной записи");
                    alert.setHeaderText(null);
                    alert.setContentText("Пользователь с таким именем уже существует");
                    alert.showAndWait();
                    loginTextField.setText("");
                } else {
                    LoginService.addNewUser();
                    LoginService.setState("login");
                    try {
                        FXRouter.goTo("Themes");
                        setWindowToCenter();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    private void setWindowToCenter() {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Stage stage = DataService.getMainStage();
        stage.setX((screenBounds.getWidth() - 800) / 2);
        stage.setY((screenBounds.getHeight() - 600) / 2);
    }
}
