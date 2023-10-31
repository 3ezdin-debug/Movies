package com.itinov.films.controller;

import com.itinov.films.model.Movie;
import com.itinov.films.model.User;
import com.itinov.films.service.MovieService;
import com.itinov.films.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private MovieService movieService;
    private static final List<String> allowedSortCriteria = Arrays.asList("date", "rating");

    @GetMapping("/{userId}/favorites/sort/{sortCriteria}")
    public ResponseEntity<?> listFavoriteMoviesSortedByCriteria(@PathVariable String userId, @RequestParam String movieId,
                                                                @PathVariable String sortCriteria) {
        logger.info("request to add movie with id : {} to 'favorite movies' for the user with id : {}", movieId, userId);
        User user = userService.getUserById(userId);
        List<Movie> movies = new ArrayList<>();
        if (allowedSortCriteria.contains(sortCriteria)) {
            if ("date".equals(sortCriteria)) {
                movies = userService.listFavoriteMoviesSortedByReleaseDate(user.getUsername());
            } else if ("rating".equals(sortCriteria)) {
                movies = userService.listFavoriteMoviesSortedByRating(user.getUsername());
            }
            return ResponseEntity.ok(movies);
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Invalid sort parameter");
        }
    }

    @PostMapping("/{userId}/favorites/add")
    public ResponseEntity<?> addToFavorites(@PathVariable String userId, @RequestParam String movieId) {
        logger.info("request to add movie with id : {} to 'favorite movies' for the user with id : {}", movieId, userId);
        User user = userService.getUserById(userId);
        Movie movie = movieService.getMovieById(movieId);
        userService.addToFavorites(user, movie);
        return ResponseEntity.ok("Added to favorites");
    }

    @DeleteMapping("/{userId}/favorites/remove")
    public ResponseEntity<?> removeFromFavorites(@PathVariable String userId, @RequestParam String movieId) {
        logger.info("request to remove movie with id : {} from 'favorite movies' for the user with id : {}", movieId, userId);
        User user = userService.getUserById(userId);
        Movie movie = movieService.getMovieById(movieId);
        userService.removeFromFavorites(user, movie);
        return ResponseEntity.ok("Removed from favorites");
    }

    @PostMapping("/{userId}/watched/add")
    public ResponseEntity<?> addToWatched(@PathVariable String userId, @RequestParam String movieId) {
        logger.info("request to mark movie with id : {} as watched for the user with id : {}", movieId, userId);
        User user = userService.getUserById(userId);
        Movie movie = movieService.getMovieById(movieId);
        userService.markMovieAsWatched(user, movie);
        return ResponseEntity.ok("Added to favorites");
    }

    @GetMapping("/{userId}/watched")
    public ResponseEntity<?> addToWatched(@PathVariable String userId) {
        logger.info("request to get list of watched movies for the user with id : {}", userId);
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(userService.listWatchedMovies(user));
    }

    @GetMapping("/{userId}/unwatched/movies")
    public Page<Movie> getUnWatchedMovies(@PathVariable String userId, @RequestParam("page") int page,
                                          @RequestParam("size") int size) {
        User user = userService.getUserById(userId);
        return userService.listUnwatchedMovies(user, page, size);
    }
}