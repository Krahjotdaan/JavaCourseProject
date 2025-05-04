package course_project.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotNull(message = "userId can not be empty")
    private Integer userId;

    @Column(nullable = false)
    @NotBlank(message = "workspaceId can not be empty")
    private String workspaceId;

    @Column(nullable = false)
    @NotBlank(message = "time can not be empty")
    private String time;
}