package org.series.website.jakarta;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Element {
    @JsonProperty("Name")
    private String Name;

    @JsonProperty("Rating")
    private String Rating;

    @JsonProperty("Situation")
    private String Situation;

    // Getters and Setters
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getSituation() {
        return Situation;
    }

    public void setSituation(String situation) {
        Situation = situation;
    }

    @Override
    public String toString() {
        return "Element{" +
                "Name='" + Name + '\'' +
                ", Rating='" + Rating + '\'' +
                ", Situation='" + Situation + '\'' +
                '}';
    }
}
