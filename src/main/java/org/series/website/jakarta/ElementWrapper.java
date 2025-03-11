package org.series.website.jakarta;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ElementWrapper {
    @JsonProperty("Elements")
    private List<Element> Elements;

    // Getter and Setter
    public List<Element> getElements() {
        return Elements;
    }

    public void setElements(List<Element> elements) {
        this.Elements = elements;
    }
}
