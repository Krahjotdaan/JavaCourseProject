package course_project.demo;

import course_project.demo.controller.BookingController;
import course_project.demo.model.Booking;
import course_project.demo.model.TemplatesAPI;
import course_project.demo.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    private Booking booking;
    private String workspace;
    private Integer user;

    @BeforeEach
    void setUp() {
        booking = new Booking();
        booking.setId(1);
    }

    @Test
    void getBooking_existingId_returnsOkResponse() {
        when(bookingService.getBooking(1)).thenReturn(booking);

        ResponseEntity<TemplatesAPI<Booking>> response = bookingController.getBooking(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatus());
        assertEquals("Бронь найдена", response.getBody().getMessage());
        assertEquals(booking, response.getBody().getData());
    }

    @Test
    void getBooking_nonExistingId_returnsNotFoundResponse() {
        when(bookingService.getBooking(1)).thenReturn(null);

        ResponseEntity<TemplatesAPI<Booking>> response = bookingController.getBooking(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().getStatus());
        assertEquals("Бронь не найдена", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void addBooking_validInput_returnsOkResponse() {
        when(bookingService.addBooking(workspace, user, booking)).thenReturn(booking);

        ResponseEntity<TemplatesAPI<Booking>> response = bookingController.addBooking(workspace, user, booking);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatus());
        assertEquals("Бронь создана", response.getBody().getMessage());
        assertEquals(booking, response.getBody().getData());
    }

    @Test
    void deleteBooking_existingId_returnsOkResponse() {
        when(bookingService.getBooking(1)).thenReturn(booking);
        doNothing().when(bookingService).deleteBooking(1);

        ResponseEntity<TemplatesAPI<String>> response = bookingController.deleteBooking(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatus());
        assertEquals("Бронь удалена", response.getBody().getMessage());
        assertEquals("OK", response.getBody().getData());

        verify(bookingService, times(1)).deleteBooking(1);
    }

    @Test
    void deleteBooking_nonExistingId_returnsNotFoundResponse() {
        when(bookingService.getBooking(1)).thenReturn(null);

        ResponseEntity<TemplatesAPI<String>> response = bookingController.deleteBooking(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().getStatus());
        assertEquals("Бронь не найдена", response.getBody().getMessage());
        assertNull(response.getBody().getData());

        verify(bookingService, never()).deleteBooking(1);
    }
}