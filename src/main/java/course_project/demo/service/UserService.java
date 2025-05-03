package course_project.demo.service;

import course_project.demo.model.User;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private final Map<Integer, User> users = new HashMap<>();
    private Integer id = 1;

    public User addUser(User user) {
        user.setId(id);
        id++;
        users.put(user.getId(), user);

        return user;
    }

    public User getUser(Integer id) {
        return users.get(id);
    }

    public void deleteUser(Integer id) {
        users.remove(id);
    }
}
