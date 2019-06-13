package learnapp.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Practice {
    private String type;
    private ArrayList<String> questions;
    private String text;
    private ArrayList<String> succes;
    private Map<Integer, ArrayList<String>> rowsMap = new HashMap<>();
    private ArrayList<Integer> pasteElements;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<String> questions) {
        this.questions = questions;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<String> getSucces() {
        return succes;
    }

    public void setSucces(ArrayList<String> succes) {
        this.succes = succes;
    }

    public Map<Integer, ArrayList<String>> getRowsMap() {
        return rowsMap;
    }

    public void setRowsMapRow(Integer rowNumber, ArrayList<String> subStrings) {
        this.rowsMap.put(rowNumber, subStrings);
    }

    public void setRowsMap(Map<Integer, ArrayList<String>> rowsMap) {
        this.rowsMap = rowsMap;
    }

    public ArrayList<Integer> getPasteElements() {
        return pasteElements;
    }

    public void setPasteElements(ArrayList<Integer> pasteElements) {
        this.pasteElements = pasteElements;
    }
}
