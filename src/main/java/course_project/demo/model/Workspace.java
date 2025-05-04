package course_project.demo.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "workspaces")
@Getter
@Setter
public class Workspace {

    @Column(nullable = false, unique = true)
    private String id;
    
    @Column(nullable = false)
    private String type;
}
