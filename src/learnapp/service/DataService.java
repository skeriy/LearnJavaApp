package learnapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataService {
    public final static String RADIO_TASK = "r_task";
    public final static String CHECKBOX_TASK = "cb_task";
    public final static String PROGRAM_LIST_TASK = "pl_task";
    public final static String DRAG_DROP_TASK = "dd_task";
    public final static String INPUT_TEXT_TASK = "it_task";
    public static JsonNode dataRootNode;

    public static JsonNode getDataRootNode() {
        return dataRootNode;
    }

    public static void setDataRootNode() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get("data/data.json"));
            dataRootNode = objectMapper.readTree(jsonData);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
