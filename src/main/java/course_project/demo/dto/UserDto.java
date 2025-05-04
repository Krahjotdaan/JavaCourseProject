package course_project.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    
    private String name;
    private String role;
    private String email;
}
