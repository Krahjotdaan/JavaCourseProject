package course_project.demo;

import course_project.demo.controller.UserController;
import course_project.demo.model.TemplatesAPI;
import course_project.demo.model.User;
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
        user.setName("John Doe");
    }

    @Test
    void getUser_existingId_returnsOkResponse() {
        when(userService.getUser(1)).thenReturn(user);

        ResponseEntity<TemplatesAPI<User>> response = userController.getUser(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatus());
        assertEquals("Пользователь найден", response.getBody().getMessage());
        assertEquals(user, response.getBody().getData());
    }

    @Test
    void getUser_nonExistingId_returnsNotFoundResponse() {
        when(userService.getUser(1)).thenReturn(null);

        ResponseEntity<TemplatesAPI<User>> response = userController.getUser(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().getStatus());
        assertEquals("Пользователь не найден", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void addUser_validInput_returnsOkResponse() {
        when(userService.addUser(user)).thenReturn(user);

        ResponseEntity<TemplatesAPI<User>> response = userController.addUser(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatus());
        assertEquals("Пользователь добавлен", response.getBody().getMessage());
        assertEquals(user, response.getBody().getData());
    }

    @Test
    void deleteUser_existingId_returnsOkResponse() {
        when(userService.getUser(1)).thenReturn(user);
        doNothing().when(userService).deleteUser(1);

        ResponseEntity<TemplatesAPI<String>> response = userController.deleteUser(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatus());
        assertEquals("Пользователь удален", response.getBody().getMessage());
        assertEquals("OK", response.getBody().getData());

        verify(userService, times(1)).deleteUser(1);
    }

    @Test
    void deleteUser_nonExistingId_returnsNotFoundResponse() {
        when(userService.getUser(1)).thenReturn(null);

        ResponseEntity<TemplatesAPI<String>> response = userController.deleteUser(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().getStatus());
        assertEquals("Пользователь не найден", response.getBody().getMessage());
        assertNull(response.getBody().getData());

        verify(userService, never()).deleteUser(1);
    }
}