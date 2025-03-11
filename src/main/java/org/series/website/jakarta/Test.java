package org.series.website.jakarta;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException {
        String json = FileUtils.readFileToString(new File("src/main/java/org/series/website/jakarta/data/data.json"), StandardCharsets.UTF_8);

        // System.out.println("Raw JSON: " + json);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ElementWrapper elementWrapper = objectMapper.readValue(json, ElementWrapper.class);

        if (elementWrapper == null) {
            System.out.println("elementWrapper is null");
        } else {
            List<Element> elements = elementWrapper.getElements();
            for (Element element : elements) {
                System.out.println(element);
            }
        }
    }
}
