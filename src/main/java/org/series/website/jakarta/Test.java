package org.series.website.jakarta;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.io.FileUtils;

import javax.swing.text.html.HTMLDocument;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.w3c.dom.Element;


//private static Element createTabel(List<JsonElement> tableContent, String[] tableHeader) {
//    Element table = document.createElement("table");
//    Element headerRow = document.createElement("tr");
//    for (String header : tableHeader) {
//        Element th = document.createElement("th");
//        th.textContent = header;
//        headerRow.appendChild(th);
//    }
//
//
//    table.appendChild(headerRow);
//    Element tr = document.createElement("tr");
//    for (JsonElement element : elements) {
//        String[] rowElements = element.toArray();
//        for (String rowElement : rowElements) {
//            Element td = document.createElement("td");
//            td.textContent = rowElement;
//            tr.appendChild(td);
//        }
//        table.appendChild(tr);
//
//    }
//    return table;
//}

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
            List<JsonElement> elements = elementWrapper.getElements();

            for (JsonElement element : elements) {
                System.out.println(element);
            }

//
//            List<JsonElement> elements = elementWrapper.getElements();
//            HTMLDocument document = new HTMLDocument();
//            document.getElementById("list-puntate").appendChild(table);


        }

        }
    }



