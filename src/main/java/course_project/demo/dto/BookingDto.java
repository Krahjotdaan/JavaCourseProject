package course_project.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookingDto {
    
    @NotNull(message = "userId can not be empty")
    private Integer userId;

    @NotBlank(message = "workspaceId can not be empty")
    private String workspaceId;

    @NotBlank(message = "time can not be empty")
    private String time;
}
