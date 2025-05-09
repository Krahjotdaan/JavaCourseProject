package course_project.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @Column(nullable = false)
    @JsonProperty("type")
    @NotBlank(message = "Workspace type cannot be blank")
    private String type;
}