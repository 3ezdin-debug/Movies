package com.itinov.films.service;

import com.itinov.films.exceptions.EntityNotFoundException;
import com.itinov.films.model.Movie;
import com.itinov.films.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MovieServiceImplTest {
    private MovieService movieService;
    private MovieRepository movieRepository;

    @BeforeEach
    public void setUp() {
        movieRepository = mock(MovieRepository.class);
        movieService = new MovieServiceImpl(movieRepository);
    }

    @Test
    public void testGetMovieById_ExistingMovie() {
        // Arrange
        String movieId = "123";
        Movie existingMovie = Movie.builder().id(movieId).build();
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(existingMovie));

        // Act
        Movie result = movieService.getMovieById(movieId);

        // Assert
        assertEquals(existingMovie, result);
    }

    @Test
    public void testGetMovieById_NonExistingMovie() {
        // Arrange
        String movieId = "456";
        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> {
            movieService.getMovieById(movieId);
        });
    }

    @Test
    public void testFindMoviesExcludingIds() {
        // Arrange
        List<String> watchedMovieIds = List.of("1", "2", "3");
        Pageable pageable = Pageable.unpaged();

        // Mock the behavior of movieRepository.findMoviesExcludingIds
        Page<Movie> expectedResultPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(movieRepository.findMoviesExcludingIds(eq(watchedMovieIds), eq(pageable)))
                .thenReturn(expectedResultPage);

        // Act
        Page<Movie> result = movieService.findMoviesExcludingIds(watchedMovieIds, pageable);

        // Assert
        assertEquals(expectedResultPage, result);
    }
}