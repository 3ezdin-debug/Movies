package com.itinov.films.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private String email;
    @DBRef
    private List<Movie> watchedMovies = new ArrayList<>();
    @DBRef
    private List<Movie> favoriteMovies = new ArrayList<>();
}