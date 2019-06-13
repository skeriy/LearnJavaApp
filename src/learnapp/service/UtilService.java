package learnapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import learnapp.pojo.Practice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

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

}
