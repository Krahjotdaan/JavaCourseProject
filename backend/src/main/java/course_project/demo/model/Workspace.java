package course_project.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "workspaces")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Workspace {

    @Id
    @Column(nullable = false, unique = true)
    @JsonProperty("id")
    @NotBlank(message = "Workspace ID cannot be blank")
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @JsonProperty("type")
    @NotNull(message = "Workspace type cannot be null")
    private Type type;

    public enum Type {
        MEETING_ROOM,
        CLASSROOM,
        LECTURE_CLASSROOM,
        LIBRARY,
        COWORKING_SPACE
    }
}