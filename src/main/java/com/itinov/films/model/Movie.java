package com.itinov.films.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@Document(collection = "movies")
public class Movie {
    @Id
    private String id;
    private String title;
    private Date releaseDate;
    private double rating;
}