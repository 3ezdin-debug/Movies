package com.itinov.films.service;

import com.itinov.films.model.Movie;
import com.itinov.films.model.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<Movie> listFavoriteMoviesSortedByReleaseDate(String username);

    List<Movie> listFavoriteMoviesSortedByRating(String username);

    User getUserById(String id);

    void addToFavorites(User user, Movie movie);

    void removeFromFavorites(User user, Movie movie);

    void markMovieAsWatched(User user, Movie movie);

    List<Movie> listWatchedMovies(User user);

    Page<Movie> listUnwatchedMovies(User user, int page, int size);

}
