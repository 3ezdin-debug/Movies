package com.itinov.films.repository;


import com.itinov.films.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, String> {
    @Query("{'_id': {$nin: ?0}}")
    Page<Movie> findMoviesExcludingIds(List<String> excludedIds, Pageable pageable);
}