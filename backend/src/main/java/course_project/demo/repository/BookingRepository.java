package course_project.demo.repository;

import course_project.demo.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

     @Query("SELECT b FROM Booking b " +
            "WHERE b.workspaceId = :workspaceId " +
            "AND b.startTime < :endTime " +
            "AND b.endTime > :startTime")
    List<Booking> findByWorkspaceIdAndStartTimeLessThanAndEndTimeGreaterThan(
            @Param("workspaceId") String workspaceId,
            @Param("endTime") LocalDateTime endTime,
            @Param("startTime") LocalDateTime startTime
    );

     @Query("SELECT b FROM Booking b " +
            "WHERE b.workspaceId = :workspaceId " +
            "AND b.startTime >= :startOfDay " +
            "AND b.startTime <= :endOfDay")
    List<Booking> findByWorkspaceIdAndStartTimeBetween(
            @Param("workspaceId") String workspaceId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );

    @Query("SELECT b FROM Booking b WHERE b.userEmail = :email")
    List<Booking> findByUserEmail(@Param("email") String email);
}