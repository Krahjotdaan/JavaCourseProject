package course_project.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import course_project.demo.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByUserId(Integer userId);
}