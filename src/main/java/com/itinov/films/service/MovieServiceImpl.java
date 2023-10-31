package com.itinov.films.service;

import com.itinov.films.exceptions.EntityNotFoundException;
import com.itinov.films.model.Movie;
import com.itinov.films.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private final MovieRepository movieRepository;

    public MovieServiceImpl(final MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie getMovieById(String id) {
        logger.info("get Movie by id : {}", id);
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        return optionalMovie.orElseThrow(() -> new EntityNotFoundException(String.format("Movie with id %s is not found.", id)));
    }

    @Override
    public Page<Movie> findMoviesExcludingIds(List<String> watchedMoviesIds, Pageable pageable) {
        logger.info("get Movies excluding by ids : {}, pageable : {}", watchedMoviesIds, pageable);
        return movieRepository.findMoviesExcludingIds(watchedMoviesIds, pageable);
    }
}