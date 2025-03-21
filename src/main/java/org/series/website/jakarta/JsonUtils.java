package org.series.website.jakarta;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JsonUtils {
    public static String[][] createTableContent() throws IOException {
        String json = FileUtils.readFileToString(new File("/home/vincent/Desktop/Repository/Series-Website/Series-Website/src/main/java/org/series/website/jakarta/data/data.json"), StandardCharsets.UTF_8);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ElementWrapper elementWrapper = objectMapper.readValue(json, ElementWrapper.class);
        String[][] tableContent = null;
        if (elementWrapper == null) {
            System.out.println("elementWrapper is null");
        } else {
            List<JsonElement> elements = elementWrapper.getElements();
            tableContent = new String[elements.size() + 1][3];
            tableContent[0] = new String[]{"Name", "Rating", "State"};
            for (int i = 0; i < elements.size(); i++){
                JsonElement element = elements.get(i);
                tableContent[i + 1] = new String[]{element.getName(), element.getRating(), element.getSituation()};
            }
        }
        return tableContent;
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
