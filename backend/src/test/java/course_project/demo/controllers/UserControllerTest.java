package course_project.demo.controllers;

import course_project.demo.controller.UserController;
import course_project.demo.exception.UserNotFoundException;
import course_project.demo.model.User;
import course_project.demo.model.TemplatesAPI;
import course_project.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
    }

    @Test
    void getBooking_existingId_returnsOkResponse() {
        when(userService.getUser(1)).thenReturn(user);

        ResponseEntity<TemplatesAPI<User>> response = userController.getUser(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatus());
        assertEquals("User found", response.getBody().getMessage());
        assertEquals(user, response.getBody().getData());
    }

    @Test
    void getUser_nonExistingId_throwsNotFound() {
        when(userService.getUser(2)).thenThrow(new UserNotFoundException(2));
        assertThrows(RuntimeException.class, () -> userController.getUser(2));
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
    void addUser_throwsRuntimeException() {
        doThrow(new RuntimeException("Test Exception")).when(userService).addUser(any(User.class));
        assertThrows(RuntimeException.class, () -> userController.addUser(new User()));
}

    @Test
    void deleteUser_existingId_returnsOkResponse() {
        doNothing().when(userService).deleteUser(1);

        ResponseEntity<TemplatesAPI<String>> response = userController.deleteUser(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatus());
        assertEquals("User deleted", response.getBody().getMessage());
        assertEquals("OK", response.getBody().getData());
    }

    @Test
    void deleteUser_nonExistingId_throwsRuntimeException() {
        doThrow(new RuntimeException("Test Exception")).when(userService).deleteUser(2);
        assertThrows(RuntimeException.class, () -> userController.deleteUser(2));
    }
}