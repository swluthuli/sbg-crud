package za.co.sbg.demo.service.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.co.sbg.demo.domain.request.UserRequest;
import za.co.sbg.demo.handler.exception.ConstraintViolationException;
import za.co.sbg.demo.handler.exception.ResourceNotFoundException;
import za.co.sbg.demo.persistence.entity.User;
import za.co.sbg.demo.persistence.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        List<User> mockUserList = Arrays.asList(
                User.builder().id(1L).name("User1").email("user1@mail.com").build(),
                User.builder().id(2L).name("User2").email("user2@mail.com").build()
        );
        when(userRepository.getAllUsers()).thenReturn(mockUserList);

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById_Success() {
        // Arrange
        User mockUser = User.builder().id(1L).name("User1").email("user1@mail.com").build();
        when(userRepository.getUser(1L)).thenReturn(mockUser);

        // Act
        User result = userService.getUserById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userRepository, times(1)).getUser(1L);
    }

    @Test
    void testCreateUser_Success() {
        // Arrange
        UserRequest userRequest = UserRequest.builder().name("User1").email("user1@mail.com").password("password").build();
        User user = User.builder().id(1L).name("User1").email("user1@mail.com").password("password").build();

        doNothing().when(userRepository).createUser(any(User.class));

        // Act
        userService.createUser(userRequest);

        // Assert
        verify(userRepository, times(1)).createUser(any(User.class));
    }

    @Test
    void testUpdateUser_Success() {
        // Arrange
        User existingUser = User.builder().id(1L).name("Old User").email("old@mail.com").build();
        UserRequest updateRequest = UserRequest.builder().name("Updated User").email("updated@mail.com").password("password").build();

        when(userRepository.getUser(1L)).thenReturn(existingUser);

        // Act
        boolean result = userService.updateUser(1L, updateRequest);

        // Assert
        assertTrue(result);
        assertEquals("Updated User", existingUser.getName());
        assertEquals("updated@mail.com", existingUser.getEmail());
        verify(userRepository, times(1)).updateUser(existingUser);
    }

    @Test
    void testDeleteUser_Success() {
        // Arrange
        User existingUser = User.builder().id(1L).name("User1").email("user1@mail.com").build();
        when(userRepository.getUser(1L)).thenReturn(existingUser);

        // Act
        boolean result = userService.deleteUser(1L);

        // Assert
        assertTrue(result);
        verify(userRepository, times(1)).deleteUser(existingUser);
    }

    @Test
    void testDeleteUser_NotFound() {
        // Arrange
        when(userRepository.getUser(1L)).thenReturn(null);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(1L));
        verify(userRepository, never()).deleteUser(any(User.class));
    }

    @Test
    void testCreateUser_ConstraintViolationException() {
        // Arrange
        UserRequest invalidRequest = UserRequest.builder().name(null).email("user1@mail.com").password("password").build();

        // Act & Assert
        assertThrows(ConstraintViolationException.class, () -> userService.createUser(invalidRequest));
        verify(userRepository, never()).createUser(any(User.class));
    }
}
