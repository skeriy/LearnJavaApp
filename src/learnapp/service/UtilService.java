package learnapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fxrouter.FXRouter;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import learnapp.pojo.Practice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class UtilService {

    public static Practice convertJsonNodeToPractice(JsonNode node) {
        Practice practiceObject = new Practice();
        practiceObject.setType(node.get("type").asText());
        practiceObject.setText(node.get("text").asText());

        if (practiceObject.getType().equals("r_task") || practiceObject.getType().equals("cb_task") || practiceObject.getType().equals("pl_task")) {
            ArrayList<String> q = new ArrayList<>();
            for (JsonNode n : node.path("questions")){
                q.add(n.asText());
            }
            practiceObject.setQuestions(q);

            ArrayList<String> s = new ArrayList<>();
            for (JsonNode n : node.path("succes")){
                s.add(n.asText());
            }
            practiceObject.setSucces(s);
        }

        if (practiceObject.getType().equals("it_task") || practiceObject.getType().equals("dd_task")) {
            if (practiceObject.getType().equals("dd_task")) {
                ArrayList<String> q = new ArrayList<>();
                for (JsonNode n : node.path("questions")){
                    q.add(n.asText());
                }
                practiceObject.setQuestions(q);
            }
            practiceObject.getRowsMap().clear();

            Iterator<JsonNode> rowNodes = node.get("rows").elements();
            Integer rowCount = 1;
            while (rowNodes.hasNext()) {
                JsonNode row = rowNodes.next();
                Iterator<JsonNode> rowSubStrings = row.elements();
                ArrayList<String> subStrings = new ArrayList<>();
                while (rowSubStrings.hasNext()) {
                    subStrings.add(rowSubStrings.next().asText());
                }
                practiceObject.setRowsMapRow(rowCount, subStrings);
                rowCount++;
            }

            ArrayList<Integer> pastElements = new ArrayList<>();
            Iterator<JsonNode> pasteElementsNode = node.get("paste").elements();
            while (pasteElementsNode.hasNext()) {
                pastElements.add(pasteElementsNode.next().asInt());
            }
            practiceObject.setPasteElements(pastElements);

            ArrayList<String> s = new ArrayList<>();
            for (JsonNode n : node.path("succes")){
                s.add(n.asText());
            }
            practiceObject.setSucces(s);
        }

        return practiceObject;
    }

    public static void showNavigatePanel(HBox navigateHBox) {

        for (int i = 1; i <= RouteService.getMaxTheory(); i++) {
            int finalId = i;
            Button theoryBtn = new Button("T" + i);
            theoryBtn.setStyle("-fx-border-radius: 100em; -fx-background-radius: 100em;");
            if (i > ProgressService.getTheory()) {
                theoryBtn.setDisable(true);
            }
            theoryBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                try {
                    RouteService.setTheory(finalId);
                    RouteService.setPractice(finalId);
                    FXRouter.goTo("theory");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            if (i == RouteService.getTheory()){
                theoryBtn.setStyle("-fx-background-color: #a2ed56;-fx-border-radius: 100em; -fx-background-radius: 100em;");
            }

            navigateHBox.getChildren().add(theoryBtn);
        }
    }

    public static Image textToImage(String text) {
        Label label = new Label(text);
        label.setPadding(new Insets(0,0,0,20));
        label.setMinSize(125, 25);
        label.setMaxSize(125, 25);
        label.setPrefSize(125, 25);
        label.setStyle("-fx-background-color: rgba(138,225,117,0.3); -fx-text-fill:rgba(0,0,0,0.83);");
        label.setWrapText(true);
        Scene scene = new Scene(new Group(label));
        WritableImage img = new WritableImage(125, 25) ;
        scene.snapshot(img);
        return img ;
    }
}
