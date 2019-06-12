package learnapp.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fxrouter.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import learnapp.service.ProgressService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

public class SubThemesController {

    @FXML
    private Button backToMenuBtn;

    @FXML
    private VBox subThemesVBox;

    @FXML
    public void onBackToMenu(){
        try {
            FXRouter.goTo("Themes");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void initialize() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get("data/data.json"));
            JsonNode rootNode = objectMapper.readTree(jsonData);
            JsonNode subThemeNode = rootNode.path("theme").path((String) FXRouter.getData()).path("sub_theme");

            ArrayList<String> subThemesNames = new ArrayList<>();
            Iterator<JsonNode> subThemesIt = subThemeNode.elements();
            while (subThemesIt.hasNext()) {
                JsonNode subTheme = subThemesIt.next();
                subThemesNames.add(subTheme.get("name").asText());
            }
            ArrayList<Button> subThemesButtons = new ArrayList<>();
            for (String name : subThemesNames) {
                Button button = new Button(name);
                button.setPrefWidth(subThemesVBox.getPrefWidth());
                button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    try {
                        FXRouter.goTo("EX1");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                subThemesButtons.add(button);
            }
            for (Button button : subThemesButtons) {
                subThemesVBox.getChildren().add(button);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private ArrayList<String> getFildNames(JsonNode node) {
        Iterator<String> fieldNames = node.fieldNames();
        ArrayList<String> result = new ArrayList<>();
        while (fieldNames.hasNext()) {
            result.add(fieldNames.next());
        }
        return result;
    }
}
