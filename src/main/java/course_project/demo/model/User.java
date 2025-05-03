package course_project.demo.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class User {

    private Integer id;

    private String name;

    private String surname;

    private String role;

    private String email;

    private List<Integer> bookings = new ArrayList<>();
}