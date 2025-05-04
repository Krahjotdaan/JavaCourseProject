package course_project.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

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
    @NotBlank(message = "ID can not be empty")
    @Size(min = 1, max = 50, message = "ID must be from 1 to 50 symbols") 
    private String id;
    
    @Column(nullable = false)
    @NotBlank(message = "type can not be empty")
    private String type;
}