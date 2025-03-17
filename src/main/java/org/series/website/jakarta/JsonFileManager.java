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

public class JsonFileManager {
    private final ObjectMapper objectMapper;
    private final File jsonFile;
    private final JsonNode rootNode;

    public JsonFileManager (String filePath) throws IOException {
        this.objectMapper =  new ObjectMapper();
        this.jsonFile = new File(filePath);
        this.rootNode = objectMapper.readTree(jsonFile);
    }

    public String[][] createTableContent() throws IOException {
        String json = FileUtils.readFileToString(this.jsonFile, StandardCharsets.UTF_8);
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ElementWrapper elementWrapper = this.objectMapper.readValue(json, ElementWrapper.class);

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

    public void jsonUpdater(String[][] data) throws IOException {
        try {
            ArrayNode elementsArray = (ArrayNode) this.rootNode.path("Elements");
            ObjectNode newSeries = this.objectMapper.createObjectNode();

            for (String[] keyValuePair : data) {
                newSeries.put(keyValuePair[0], keyValuePair[1]);
            }

            elementsArray.add(newSeries);
            this.objectMapper.writerWithDefaultPrettyPrinter().writeValue(this.jsonFile, this.rootNode);

        } catch (IOException e) {
            throw new IOException("Failed to update JSON file", e);
        }
    }
}