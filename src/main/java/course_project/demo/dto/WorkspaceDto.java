package course_project.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WorkspaceDto {
    
    @NotBlank(message = "ID can not be empty")
    @Size(min = 1, max = 50, message = "ID must be from 1 to 50 symbols") 
    private String id;

    @NotBlank(message = "type can not be empty")
    private String type;
}
