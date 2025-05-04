package course_project.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotBlank(message = "name can not be empty")
    @Size(min = 2, max = 50, message = "name must be from 2 to 50 symbols")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "role can not be empty")
    private String role;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email can not be empty")
    @Email(message = "Incorrect email")
    private String email;
}