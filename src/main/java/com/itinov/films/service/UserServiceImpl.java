package com.itinov.films.service;

import com.itinov.films.exceptions.EntityNotFoundException;
import com.itinov.films.model.Movie;
import com.itinov.films.model.User;
import com.itinov.films.repository.MovieRepository;
import com.itinov.films.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final MovieService movieService;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository, final MovieService movieService) {
        this.userRepository = userRepository;
        this.movieService = movieService;
    }

    public List<Movie> listFavoriteMoviesSortedByReleaseDate(String username) {
        logger.info("retrieving list of favorite movies for username : {} sorted by release date", username);
        User user = userRepository.findByUsername(username);

        if (user != null) {
            List<Movie> favoriteMovies = user.getFavoriteMovies();

            // Sort the favoriteMovies by releaseDate
            favoriteMovies.sort(Comparator.comparing(Movie::getReleaseDate));

            return favoriteMovies;
        }

        return Collections.emptyList(); // User not found or has no favorite movies
    }

    @Override
    public List<Movie> listFavoriteMoviesSortedByRating(String username) {
        logger.info("retrieving list of favorite movies for username : {} sorted by rating", username);
        User user = userRepository.findByUsername(username);

        if (user != null) {
            List<Movie> favoriteMovies = user.getFavoriteMovies();

            // Sort the favoriteMovies by rating (in descending order)
            favoriteMovies.sort((movie1, movie2) -> Double.compare(movie2.getRating(), movie1.getRating()));

            return favoriteMovies;
        }

        return Collections.emptyList(); // User not found or has no favorite movies
    }

    @Override
    public User getUserById(String id) {
        logger.info("getting user by id : {}", id);
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElseThrow(() -> new EntityNotFoundException(String.format("User with id %s is not found.", id)));
    }

    public void addToFavorites(User user, Movie movie) {
        logger.info("adding movie with id : {} to list of 'favorite movies' for the user with id : {}", movie.getId(), user.getId());
        user.getFavoriteMovies().add(movie);
        userRepository.save(user);
    }

    public void removeFromFavorites(User user, Movie movie) {
        logger.info("removing movie with id : {} from the list of 'favorite movies' for the user with id : {}", movie.getId(), user.getId());
        user.getFavoriteMovies().remove(movie);
        userRepository.save(user);
    }

    @Override
    public void markMovieAsWatched(User user, Movie movie) {
        logger.info("marking movie with id : {} as watched for the user with id : {}", movie.getId(), user.getId());
        user.getWatchedMovies().add(movie);
        userRepository.save(user);
    }

    @Override
    public List<Movie> listWatchedMovies(User user) {
        logger.info("listing watched movies for the user with id : {}", user.getId());
        return user.getWatchedMovies();
    }

    @Override
    public Page<Movie> listUnwatchedMovies(User user, int page, int size) {
        logger.info("listing unwatched movies for the user with id : {}", user.getId());
        List<String> watchedMoviesIds = user.getWatchedMovies().stream().map(Movie::getId).toList();
        Pageable pageable = PageRequest.of(page, size);
        return movieService.findMoviesExcludingIds(watchedMoviesIds, pageable);
    }
}
