package course_project.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    
    @NotBlank(message = "name can not be empty")
    @Size(min = 2, max = 50, message = "name must be from 2 to 50 symbols")
    private String name;

    @NotBlank(message = "role can not be empty")
    private String role;

    @NotBlank(message = "Email can not be empty")
    @Email(message = "Incorrect email")
    private String email;
}
