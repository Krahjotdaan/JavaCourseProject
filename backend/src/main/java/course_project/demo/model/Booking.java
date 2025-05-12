package course_project.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonView;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Booking {

    @Id
    @JsonView(Views.Internal.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @JsonView(Views.Public.class)
    @NotNull(message = "User email cannot be null")
    private String userEmail;

    @Column(nullable = false)
    @JsonView(Views.Public.class)
    @NotBlank(message = "Workspace ID cannot be blank")
    private String workspaceId;

    @Column(nullable = false)
    @JsonView(Views.Public.class)
    @NotNull(message = "Start time cannot be null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startTime;

    @Column(nullable = false)
    @JsonView(Views.Public.class)
    @NotNull(message = "End time cannot be null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
    private LocalDateTime endTime;
}