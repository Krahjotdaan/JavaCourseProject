package course_project.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import course_project.demo.model.TemplatesAPI;
import course_project.demo.model.User;
import course_project.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "User", description = "User API")
@RequestMapping("/users")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Получение пользователя")
    @GetMapping("/{id}")
    public ResponseEntity<TemplatesAPI<User>> getUser(@PathVariable Integer id) {
        
        User user = userService.getUser(id);

        if (user != null) {
            return ResponseEntity.ok(new TemplatesAPI<>(200, "Пользователь найден", user));
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new TemplatesAPI<>(404, "Пользователь не найден", null));
        }
    }

    @Operation(summary = "Добавление пользователя")
    @PostMapping
    public ResponseEntity<TemplatesAPI<User>> addUser(@Valid @RequestBody User user) {
        
        User newUser = userService.addUser(user);  

        return ResponseEntity.ok(new TemplatesAPI<>(200, "Пользователь добавлен", newUser));
    }

    @Operation(summary = "Удаление пользователя")
    @DeleteMapping("/{id}")
    public ResponseEntity<TemplatesAPI<String>> deleteUser(@PathVariable Integer id) {

        if (userService.getUser(id) != null) {
            userService.deleteUser(id);

            return ResponseEntity.ok(new TemplatesAPI<>(200, "Пользователь удален", "OK"));
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new TemplatesAPI<>(404, "Пользователь не найден", null));
        }
    }
}
