package course_project.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.*;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Booking {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @JsonProperty("userId")
    @NotNull(message = "User ID cannot be null")
    private Integer userId;

    @Column(nullable = false)
    @JsonProperty("workspaceId")
    @NotBlank(message = "Workspace ID cannot be blank")
    private String workspaceId;

    @Column(nullable = false)
    @JsonProperty("time")
    @NotBlank(message = "Time cannot be blank")
    private String time;
}