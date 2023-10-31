package com.itinov.films.service;

import com.itinov.films.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieService {
    Movie getMovieById(String id);

    Page<Movie> findMoviesExcludingIds(List<String> watchedMoviesIds, Pageable pageable);
}