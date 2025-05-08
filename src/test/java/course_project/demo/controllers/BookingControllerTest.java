package course_project.demo.controllers;

import course_project.demo.controller.BookingController;
import course_project.demo.exception.BookingNotFoundException;
import course_project.demo.model.Booking;
import course_project.demo.model.TemplatesAPI;
import course_project.demo.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @MockBean
    private BookingService bookingService;

    @Autowired
    private BookingController bookingController;

    private Booking booking;
    private Integer bookingId;

    @BeforeEach
    void setUp() {
        bookingId = 1;
        booking = new Booking();
        booking.setId(bookingId);
    }

    @Test
    void getBooking_existingId_returnsOkResponse() {
        when(bookingService.getBooking(bookingId)).thenReturn(booking);

        ResponseEntity<TemplatesAPI<Booking>> response = bookingController.getBooking(bookingId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatus());
        assertEquals("Booking found", response.getBody().getMessage());
        assertEquals(booking, response.getBody().getData());
    }

    @Test
    void getBooking_nonExistingId_returnsNotFoundResponse() {
        String errorMessage = "Booking not found with id: " + bookingId;
        when(bookingService.getBooking(bookingId)).thenThrow(new BookingNotFoundException(bookingId));

        ResponseEntity<TemplatesAPI<Booking>> response = bookingController.getBooking(bookingId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void addBooking_validInput_returnsOkResponse() {
        when(bookingService.addBooking(booking)).thenReturn(booking);

        ResponseEntity<TemplatesAPI<Booking>> response = bookingController.addBooking(booking);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatus());
        assertEquals("Booking created", response.getBody().getMessage());
        assertEquals(booking, response.getBody().getData());
    }

    @Test
    void deleteBooking_existingId_returnsOkResponse() {
        when(bookingService.getBooking(bookingId)).thenReturn(booking);

        ResponseEntity<TemplatesAPI<String>> response = bookingController.deleteBooking(bookingId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatus());
        assertEquals("Booking deleted", response.getBody().getMessage());
        assertEquals("OK", response.getBody().getData());

        verify(bookingService, times(1)).deleteBooking(bookingId);
    }

    @Test
    void deleteBooking_nonExistingId_returnsNotFoundResponse() {
        String errorMessage = "Booking not found with id: " + bookingId;
        when(bookingService.getBooking(bookingId)).thenThrow(new BookingNotFoundException(bookingId));

        ResponseEntity<TemplatesAPI<String>> response = bookingController.deleteBooking(bookingId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertNull(response.getBody().getData());

        verify(bookingService, never()).deleteBooking(bookingId);
    }
}