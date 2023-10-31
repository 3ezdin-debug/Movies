package com.itinov.films.service;

import com.itinov.films.exceptions.EntityNotFoundException;
import com.itinov.films.model.Movie;
import com.itinov.films.model.User;
import com.itinov.films.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserService userService;
    private UserRepository userRepository;
    private MovieService movieService;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        movieService = mock(MovieService.class);
        userService = new UserServiceImpl(userRepository, movieService);
    }

    @Test
    public void testGetUserById_ExistingUser() {
        // Arrange
        String userId = "123";
        User existingUser = User.builder().id(userId).build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        // Act
        User result = userService.getUserById(userId);

        // Assert
        assertEquals(existingUser, result);
    }

    @Test
    public void testGetUserById_NonExistingUser() {
        // Arrange
        String userId = "456";
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> {
            userService.getUserById(userId);
        });
    }

    @Test
    public void testAddToFavorites() {
        // Arrange
        User user = User.builder().id("testUser").build();
        Movie movie = Movie.builder().id("123").title("Test Movie").build();

        when(userRepository.save(user)).thenReturn(user);

        // Act
        userService.addToFavorites(user, movie);

        // Assert
        assertTrue(user.getFavoriteMovies().contains(movie));
        verify(userRepository, times(1)).save(user);
    }
}