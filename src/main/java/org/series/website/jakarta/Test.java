package org.series.website.jakarta;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

// Class only for testing
public class Test {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File jsonFile = new File("src/main/java/org/series/website/jakarta/data/data.json");
        JsonNode rootNode = objectMapper.readTree(jsonFile);
        String [][] row0 = {{"Name", "Andor"},{"Rating", "9"},{"Situation", "1+"}};
        String [][] row1 = {{"Name", "Supernatural"},{"Rating", "9"},{"Situation", "Abgeschlossen"}};
        jsonFile = JsonUpdater(jsonFile, objectMapper, rootNode, row0);
        jsonFile = JsonUpdater(jsonFile, objectMapper, rootNode, row1);
    }
    public static File JsonUpdater(File jsonFile, ObjectMapper objectMapper, JsonNode rootNode, String[][] data){
        try {
            ArrayNode elementsArray = (ArrayNode) rootNode.path("Elements");
            ObjectNode newSeries = objectMapper.createObjectNode();

            for (String[] keyValuePair : data) {
                newSeries.put(keyValuePair[0], keyValuePair[1]);
            }

            elementsArray.add(newSeries);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, rootNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    return jsonFile;
    }
}

