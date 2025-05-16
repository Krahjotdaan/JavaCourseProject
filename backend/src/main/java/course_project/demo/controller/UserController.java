package course_project.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import course_project.demo.model.TemplatesAPI;
import course_project.demo.model.User;
import course_project.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@Tag(name = "User", description = "User API")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Получение пользователя")
    @GetMapping("/{id}")
    public ResponseEntity<TemplatesAPI<User>> getUser(@PathVariable Integer id) {
        try {
            User user = userService.getUser(id);
            return ResponseEntity.ok(new TemplatesAPI<>(200, "User found", user));
        } 
        catch (Exception e) {
            logger.error("An unexpected error occurred while getting user with id: {}", id, e);
            throw e; 
        }
    }

    @Operation(summary = "Добавление пользователя")
    @Transactional
    @PostMapping
    public ResponseEntity<TemplatesAPI<User>> addUser(@Valid @RequestBody User user) {
        try {
            User newUser = userService.addUser(user);
            return ResponseEntity.ok(new TemplatesAPI<>(200, "User added", newUser));
        } 
        catch (Exception e) {
            logger.error("An unexpected error occurred while adding user: {}", e.getMessage(), e);
            throw e; 
        }
    }

    @Operation(summary = "Удаление пользователя")
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<TemplatesAPI<String>> deleteUser(@PathVariable Integer id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(new TemplatesAPI<>(200, "User deleted", "OK"));
        } 
        catch (Exception e) {
            logger.error("An unexpected error occurred while deleting user with id: {}", id, e);
            throw e; 
        }
    }
}