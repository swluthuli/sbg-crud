package za.co.sbg.demo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.co.sbg.demo.domain.request.UserRequest;
import za.co.sbg.demo.persistence.entity.User;
import za.co.sbg.demo.service.UserService;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController; // Controller to test

    @Mock
    private UserService userService; // Mocked service

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        List<User> mockUserList = Arrays.asList(
                User.builder().id(1L).name("User1").email("user1@mail.com").password("password1").build(),
                User.builder().id(2L).name("User2").email("user2@mail.com").password("password2").build()
        );
        when(userService.getAllUsers()).thenReturn(mockUserList);

        // Act
        List<User> users = userController.getAllUsers();

        // Assert
        assertNotNull(users);
        assertEquals(2, users.size());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById_Success() {
        // Arrange
        User mockUser = User.builder().id(1L).name("User1").email("user1@mail.com").password("password1").build();
        when(userService.getUserById(1L)).thenReturn(mockUser);

        // Act
        User user = userController.getUserById(1L);

        // Assert
        assertNotNull(user);
        assertEquals("User1", user.getName());
        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testGetUserById_NotFound() {
        // Arrange
        when(userService.getUserById(1L)).thenReturn(null);

        // Act
        User user = userController.getUserById(1L);

        // Assert
        assertNull(user);
        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testAddUser() {
        // Arrange
        UserRequest mockRequest = UserRequest.builder()
                .name("New User")
                .email("newuser@mail.com")
                .password("newpassword")
                .build();

        // Act
        Response response = userController.addUser(mockRequest);

        // Assert
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(mockRequest, response.getEntity());
        verify(userService, times(1)).createUser(mockRequest);
    }

    @Test
    void testUpdateUser_Success() {
        // Arrange
        UserRequest mockRequest = UserRequest.builder()
                .name("Updated User")
                .email("updateduser@mail.com")
                .password("updatedpassword")
                .build();

        when(userService.updateUser(1L, mockRequest)).thenReturn(true);

        // Act
        Response response = userController.updateUser(mockRequest, 1L);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(userService, times(1)).updateUser(1L, mockRequest);
    }

    @Test
    void testUpdateUser_NotFound() {
        // Arrange
        UserRequest mockRequest = UserRequest.builder()
                .name("Updated User")
                .email("updateduser@mail.com")
                .password("updatedpassword")
                .build();

        when(userService.updateUser(1L, mockRequest)).thenReturn(false);

        // Act
        Response response = userController.updateUser(mockRequest, 1L);

        // Assert
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        verify(userService, times(1)).updateUser(1L, mockRequest);
    }

    @Test
    void testDeleteUser_Success() {
        // Arrange
        when(userService.deleteUser(1L)).thenReturn(true);

        // Act
        Response response = userController.deleteUser(1L);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    void testDeleteUser_NotFound() {
        // Arrange
        when(userService.deleteUser(1L)).thenReturn(false);

        // Act
        Response response = userController.deleteUser(1L);

        // Assert
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        verify(userService, times(1)).deleteUser(1L);
    }
}
