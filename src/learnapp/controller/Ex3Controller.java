package learnapp.controller;

import com.github.fxrouter.FXRouter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class Ex3Controller {
    @FXML
    private VBox codeVBox;

    @FXML
    private Button backToMenuBtn;

    @FXML
    private Button checkBtn;

    @FXML
    public void onBackToMenu() throws IOException {
        FXRouter.goTo("Menu");
    }

    @FXML
    public void onCheck() {
        String result = "";
        for (TextField t : allTextFields) {
            result += t.getText() + " ";
        }
        System.out.println("on check: " + result);
    }

    private ObservableList<TextField> allTextFields = FXCollections.observableArrayList();

    public void initialize() {
        HBox row1 = new HBox();
        HBox row2 = new HBox();
        HBox row3 = new HBox();
        TextField t1 = new TextField();
        TextField t2 = new TextField();
        t1.setPrefWidth(50);
        t2.setPrefWidth(50);
        row1.getChildren().addAll(new Text("public static void "), t1, new Text((" (String[] args) {")));
        row2.getChildren().addAll(new Text("    System.out.print("), t2, new Text((");")));
        row3.getChildren().addAll(new Text("}"));
        codeVBox.getChildren().add(row1);
        codeVBox.getChildren().add(row2);
        codeVBox.getChildren().add(row3);

        allTextFields.addAll(t1, t2);
    }
}
