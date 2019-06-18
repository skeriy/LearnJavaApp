package learnapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import learnapp.pojo.User;

import java.util.*;

public class UserService {
    private static ObservableList<String> users;

    public static ObservableList<User> getAllUsers() {
        ObservableList<User> resultList = FXCollections.observableArrayList();
        JsonNode progressNode = DataService.getRootProgress();
        List<String> allUserNames = LoginService.getSavedUsers();
        Iterator<JsonNode> userProgressIt = progressNode.elements();
        List<User> allUsers = new ArrayList<>();

        int nameCount = 0;
        while (userProgressIt.hasNext()) {
            JsonNode progress = userProgressIt.next();
            if (allUserNames.get(nameCount).equals("admin")) {
                nameCount++;
                continue;
            }
            String name = allUserNames.get(nameCount);
            String theme = progress.path("theme").asText();
            String subTheme = progress.path("sub_theme").asText();
            String theory = progress.path("theory").asText();
            String practice = progress.path("practice").asText();
            Button button = new Button("Удалить");
            button.setId(name);
            button.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff;");

            button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                String userName = ((Button) event.getSource()).getId();
                ProgressService.deleteUser(userName);
                deleteUserFromTable(resultList, userName);

            });

            allUsers.add(new User(name, theme, subTheme, theory, practice, button));
            nameCount++;
            System.out.println();
        }

        resultList.addAll(allUsers);
        return resultList;
    }

    private static void deleteUserFromTable(ObservableList<User> users, String name) {
        int deleteUserNumer = 0;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getName().equals(name)) {
                deleteUserNumer = i;
                break;
            }
        }
        users.remove(deleteUserNumer);
    }
}
