package course_project.demo.controllers;

import course_project.demo.controller.UserController;
import course_project.demo.exception.UserNotFoundException;
import course_project.demo.model.TemplatesAPI;
import course_project.demo.model.User;
import course_project.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private UserController userController;

    private User user;
    private Integer userId;

    @BeforeEach
    void setUp() {
        userId = 1;
        user = new User();
        user.setId(userId);
        user.setName("John Doe");
    }

    @Test
    void getUser_existingId_returnsOkResponse() {
        when(userService.getUser(userId)).thenReturn(user);

        ResponseEntity<TemplatesAPI<User>> response = userController.getUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatus());
        assertEquals("User found", response.getBody().getMessage());
        assertEquals(user, response.getBody().getData());
    }

    @Test
    void getUser_nonExistingId_returnsNotFoundResponse() {
        String errorMessage = "User not found with id: " + userId;
        when(userService.getUser(userId)).thenThrow(new UserNotFoundException(userId));

        ResponseEntity<TemplatesAPI<User>> response = userController.getUser(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void addUser_validInput_returnsOkResponse() {
        when(userService.addUser(user)).thenReturn(user);

        ResponseEntity<TemplatesAPI<User>> response = userController.addUser(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatus());
        assertEquals("User added", response.getBody().getMessage());
        assertEquals(user, response.getBody().getData());
    }

    @Test
    void deleteUser_existingId_returnsOkResponse() {
        when(userService.getUser(userId)).thenReturn(user);

        ResponseEntity<TemplatesAPI<String>> response = userController.deleteUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatus());
        assertEquals("User deleted", response.getBody().getMessage());
        assertEquals("OK", response.getBody().getData());

        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    void deleteUser_nonExistingId_returnsNotFoundResponse() {
        String errorMessage = "User not found with id: " + userId;
        when(userService.getUser(userId)).thenThrow(new UserNotFoundException(userId));

        ResponseEntity<TemplatesAPI<String>> response = userController.deleteUser(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertNull(response.getBody().getData());

        verify(userService, never()).deleteUser(userId);
    }
}