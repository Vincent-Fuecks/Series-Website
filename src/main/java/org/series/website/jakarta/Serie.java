package org.series.website.jakarta;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "serie")
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonbProperty("id")
    public Long id;

    @Column(name = "name")
    @JsonbProperty("name")
    public String name;

    @Column(name = "rating")
    @JsonbProperty("rating")
    public int rating;

    @Column(name = "status")
    @JsonbProperty("status")
    public String status;

    public void setName(String name) { this.name = name; }
    public void setRating(int rating) { this.rating = rating; }
    public void setStatus(String status) { this.status = status; }


}

