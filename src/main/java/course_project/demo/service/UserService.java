package course_project.demo.service;

import course_project.demo.dto.UserDto;
import course_project.demo.model.User;
import course_project.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(UserDto userDto) {
        User newUser = new User();
        
        newUser.setName(userDto.getName());
        newUser.setRole(userDto.getRole());
        newUser.setEmail(userDto.getEmail());

        return userRepository.save(newUser);
    }

    public User getUser(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public User updateUser(Integer id, User updatedUser) {
        User user = getUser(id);

        user.setName(updatedUser.getName());
        user.setRole(updatedUser.getRole());
        user.setEmail(updatedUser.getEmail());

        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
		}
		else {
			throw new EntityNotFoundException("User not found");
		}
    }
}
