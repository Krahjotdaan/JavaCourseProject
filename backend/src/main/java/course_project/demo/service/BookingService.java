package course_project.demo.service;

import course_project.demo.model.Booking;
import course_project.demo.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, WorkspaceRepository workspaceRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.workspaceRepository = workspaceRepository;
        this.userRepository = userRepository;
    }

     public List<Booking> getOccupiedIntervals(String workspaceId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        return bookingRepository.findByWorkspaceIdAndStartTimeBetween(
                workspaceId, startOfDay, endOfDay);
    }

    public Booking createBooking(Booking booking) {
        
        if (!userRepository.existsByEmail(booking.getUserEmail())) {
            throw new EntityNotFoundException("User not found");
        }
        if (!workspaceRepository.existsById(booking.getWorkspaceId())) {
            throw new EntityNotFoundException("Workspace not found");
        }

        Duration duration = Duration.between(booking.getStartTime(), booking.getEndTime());
        if (duration.toMinutes() > 90) {
            throw new IllegalArgumentException("Booking cannot exceed 1.5 hours");
        }

        List<Booking> existingBookings = bookingRepository.findByWorkspaceIdAndStartTimeLessThanAndEndTimeGreaterThan(
                booking.getWorkspaceId(), booking.getEndTime(), booking.getStartTime());

        if (!existingBookings.isEmpty()) {
            throw new IllegalArgumentException("Booking overlaps with an existing booking");
        }

        return bookingRepository.save(booking);
    }

    public Booking getBooking(Integer id) {
        return bookingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Booking not found"));
    }

    public List<Booking> getBookingsByEmail(String email) {
        return bookingRepository.findByUserEmail(email);
    }

    public void deleteBooking(Integer id) {
        bookingRepository.deleteById(id);
    }
}