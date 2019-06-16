package learnapp.pojo;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class User {
    private final StringProperty name;
    private final StringProperty userTheme;
    private final StringProperty userSubTheme;
    private final StringProperty userTheory;
    private final StringProperty userPractice;
    private final ObjectProperty<Button> userButton;

    public User(String name, String userTheme, String userSubTheme, String userTheory, String userPractice, Button userButton) {
        this.name = new SimpleStringProperty(name);
        this.userTheme = new SimpleStringProperty(userTheme);
        this.userSubTheme = new SimpleStringProperty(userSubTheme);
        this.userTheory = new SimpleStringProperty(userTheory);
        this.userPractice = new SimpleStringProperty(userPractice);
        this.userButton = new SimpleObjectProperty<>(userButton);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getUserTheme() {
        return userTheme.get();
    }

    public StringProperty userThemeProperty() {
        return userTheme;
    }

    public void setUserTheme(String userTheme) {
        this.userTheme.set(userTheme);
    }

    public String getUserSubTheme() {
        return userSubTheme.get();
    }

    public StringProperty userSubThemeProperty() {
        return userSubTheme;
    }

    public void setUserSubTheme(String userSubTheme) {
        this.userSubTheme.set(userSubTheme);
    }

    public String getUserTheory() {
        return userTheory.get();
    }

    public StringProperty userTheoryProperty() {
        return userTheory;
    }

    public void setUserTheory(String userTheory) {
        this.userTheory.set(userTheory);
    }

    public String getUserPractice() {
        return userPractice.get();
    }

    public StringProperty userPracticeProperty() {
        return userPractice;
    }

    public void setUserPractice(String userPractice) {
        this.userPractice.set(userPractice);
    }

    public Button getUserButton() {
        return userButton.get();
    }

    public ObjectProperty<Button> userButtonProperty() {
        return userButton;
    }

    public void setUserButton(Button userButton) {
        this.userButton.set(userButton);
    }
}
