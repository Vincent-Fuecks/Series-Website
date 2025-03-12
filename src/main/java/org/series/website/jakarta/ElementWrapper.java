package org.series.website.jakarta;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ElementWrapper {
    @JsonProperty("Elements")
    private List<JsonElement> Elements;

    // Getter and Setter
    public List<JsonElement> getElements() {
        return Elements;
    }

    public void setElements(List<JsonElement> elements) {
        this.Elements = elements;
    }
}