package course_project.demo.unit;

import course_project.demo.model.Booking;
import course_project.demo.repository.BookingRepository;
import course_project.demo.repository.UserRepository;
import course_project.demo.repository.WorkspaceRepository;
import course_project.demo.service.BookingService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class BookingControllerTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private WorkspaceRepository workspaceRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getOccupiedIntervals_ReturnsListOfBookings() {
        String workspaceId = "testWorkspace";
        LocalDate date = LocalDate.now();
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(java.time.LocalTime.MAX);
        List<Booking> expectedBookings = new ArrayList<>();
        expectedBookings.add(new Booking());
        when(bookingRepository.findByWorkspaceIdAndStartTimeBetween(workspaceId, startOfDay, endOfDay)).thenReturn(expectedBookings);

        List<Booking> actualBookings = bookingService.getOccupiedIntervals(workspaceId, date);

        assertEquals(expectedBookings, actualBookings);
    }

     @Test
    void createBooking_ValidBooking_ReturnsSavedBooking() {
        Booking booking = new Booking();
        booking.setUserEmail("test@example.com");
        booking.setWorkspaceId("testWorkspace");
        booking.setStartTime(LocalDateTime.now().plusHours(1));
        booking.setEndTime(LocalDateTime.now().plusHours(2));
        when(userRepository.existsByEmail(booking.getUserEmail())).thenReturn(true);
        when(workspaceRepository.existsById(booking.getWorkspaceId())).thenReturn(true);
        when(bookingRepository.findByWorkspaceIdAndStartTimeLessThanAndEndTimeGreaterThan(
            booking.getWorkspaceId(), booking.getEndTime(), booking.getStartTime())).thenReturn(new ArrayList<>());
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking savedBooking = bookingService.createBooking(booking);

        assertNotNull(savedBooking);
        assertEquals(booking, savedBooking);
    }

    @Test
    void createBooking_UserNotFound_ThrowsEntityNotFoundException() {
        Booking booking = new Booking();
        booking.setUserEmail("test@example.com");
        when(userRepository.existsByEmail(booking.getUserEmail())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> bookingService.createBooking(booking));
    }

    @Test
    void createBooking_WorkspaceNotFound_ThrowsEntityNotFoundException() {
        Booking booking = new Booking();
        booking.setUserEmail("test@example.com");
        booking.setWorkspaceId("testWorkspace");
        when(userRepository.existsByEmail(booking.getUserEmail())).thenReturn(true);
        when(workspaceRepository.existsById(booking.getWorkspaceId())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> bookingService.createBooking(booking));
    }

    @Test
    void createBooking_BookingDurationExceedsLimit_ThrowsIllegalArgumentException() {
        Booking booking = new Booking();
        booking.setUserEmail("test@example.com");
        booking.setWorkspaceId("testWorkspace");
        booking.setStartTime(LocalDateTime.now());
        booking.setEndTime(LocalDateTime.now().plusHours(2)); // Duration is 2 hours
        when(userRepository.existsByEmail(booking.getUserEmail())).thenReturn(true);
        when(workspaceRepository.existsById(booking.getWorkspaceId())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> bookingService.createBooking(booking));
    }

    @Test
    void createBooking_BookingOverlapsWithExisting_ThrowsIllegalArgumentException() {
        Booking booking = new Booking();
        booking.setUserEmail("test@example.com");
        booking.setWorkspaceId("testWorkspace");
        booking.setStartTime(LocalDateTime.now().plusHours(1));
        booking.setEndTime(LocalDateTime.now().plusHours(2));
        List<Booking> overlappingBookings = new ArrayList<>();
        overlappingBookings.add(new Booking());
        when(userRepository.existsByEmail(booking.getUserEmail())).thenReturn(true);
        when(workspaceRepository.existsById(booking.getWorkspaceId())).thenReturn(true);
        when(bookingRepository.findByWorkspaceIdAndStartTimeLessThanAndEndTimeGreaterThan(
            booking.getWorkspaceId(), booking.getEndTime(), booking.getStartTime())).thenReturn(overlappingBookings);

        assertThrows(IllegalArgumentException.class, () -> bookingService.createBooking(booking));
    }

    @Test
    void getBooking_ExistingBookingId_ReturnsBooking() {
        Integer bookingId = 1;
        Booking expectedBooking = new Booking();
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(expectedBooking));

        Booking actualBooking = bookingService.getBooking(bookingId);

        assertEquals(expectedBooking, actualBooking);
    }

    @Test
    void getBooking_NonExistingBookingId_ThrowsEntityNotFoundException() {
        Integer bookingId = 1;
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookingService.getBooking(bookingId));
    }

    @Test
    void getBookingsByEmail_ExistingEmail_ReturnsListOfBookings() {
        String userEmail = "test@example.com";
        List<Booking> expectedBookings = new ArrayList<>();
        expectedBookings.add(new Booking());
        when(bookingRepository.findByUserEmail(userEmail)).thenReturn(expectedBookings);

        List<Booking> actualBookings = bookingService.getBookingsByEmail(userEmail);

        assertEquals(expectedBookings, actualBookings);
    }

    @Test
    void deleteBooking_ExistingBookingId_DeletesBooking() {
        Integer bookingId = 1;

        bookingService.deleteBooking(bookingId);

        verify(bookingRepository, times(1)).deleteById(bookingId);
    }
} 
