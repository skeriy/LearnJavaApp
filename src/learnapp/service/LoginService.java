package learnapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.*;

public class LoginService {
    private static String state = "login";
    private static String login = "";
    public static boolean adminMode;

    public static List<String> getSavedUsers() {
        List<String> users = new ArrayList<>();
        JsonNode allUsers = DataService.getRootProgress();
        Iterator<String> allUsersIt = allUsers.fieldNames();

        while (allUsersIt.hasNext()) {
            users.add(allUsersIt.next());
        }
        return users;
    }

    public static boolean isUserExist(String login) {
        return getSavedUsers().contains(login);
    }

    public static void addNewUser() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode newUser = mapper.createObjectNode();
        ObjectNode properties = mapper.createObjectNode();
        properties.put("theme", 1);
        properties.put("sub_theme", 1);
        properties.put("theory", 1);
        properties.put("practice", 1);
        newUser.set(LoginService.getLogin(), properties);
        ((ObjectNode) DataService.getRootProgress()).setAll(newUser);
        JsonNode jsonNode = DataService.getRootProgress();

        System.out.println();
    }

    public static String getState() {
        return state;
    }

    public static void setState(String state) {
        LoginService.state = state;
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        LoginService.login = login;
    }

    public static boolean isAdminMode() {
        return adminMode;
    }

    public static void setAdminMode(boolean adminMode) {
        LoginService.adminMode = adminMode;
    }
}
