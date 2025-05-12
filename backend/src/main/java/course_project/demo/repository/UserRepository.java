package course_project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import course_project.demo.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    boolean existsByEmail(String email);
}