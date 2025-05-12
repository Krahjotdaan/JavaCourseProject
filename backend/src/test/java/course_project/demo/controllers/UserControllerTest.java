package course_project.demo.controllers;

import course_project.demo.controller.UserController;
import course_project.demo.model.TemplatesAPI;
import course_project.demo.model.User;
import course_project.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUser_ExistingUserId_ReturnsOkResponse() {
        Integer userId = 1;
        User expectedUser = new User();
        expectedUser.setId(userId);
        when(userService.getUser(userId)).thenReturn(expectedUser);

        ResponseEntity<TemplatesAPI<User>> response = userController.getUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(200, response.getBody().getStatus());
        assertEquals("User found", response.getBody().getMessage());
        assertEquals(expectedUser, response.getBody().getData());
    }

    @Test
    void addUser_ValidUser_ReturnsOkResponse() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setRole(User.Role.STUDENT);
        when(userService.addUser(any(User.class))).thenReturn(user);

        ResponseEntity<TemplatesAPI<User>> response = userController.addUser(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(200, response.getBody().getStatus());
        assertEquals("User added", response.getBody().getMessage());
        assertEquals(user, response.getBody().getData());
    }

    @Test
    void deleteUser_ExistingUserId_ReturnsOkResponse() {
        Integer userId = 1;

        ResponseEntity<TemplatesAPI<String>> response = userController.deleteUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(200, response.getBody().getStatus());
        assertEquals("User deleted", response.getBody().getMessage());
        assertEquals("OK", response.getBody().getData());
    }
}