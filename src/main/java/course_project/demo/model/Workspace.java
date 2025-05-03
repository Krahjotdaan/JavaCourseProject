package course_project.demo.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Workspace {

    private String id;
    
    private String type;

    private List<Integer> bookings = new ArrayList<>();
}
