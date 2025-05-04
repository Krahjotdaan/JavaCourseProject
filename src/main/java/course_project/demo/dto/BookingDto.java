package course_project.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookingDto {
    
    private Integer userId;
    private String workspaceId;
    private String time;
}
