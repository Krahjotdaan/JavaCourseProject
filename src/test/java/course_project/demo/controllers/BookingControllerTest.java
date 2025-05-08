package course_project.demo.controllers;

import course_project.demo.controller.BookingController;
import course_project.demo.exception.BookingNotFoundException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    private Booking booking;

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
        assertEquals("Booking found", response.getBody().getMessage());
        assertEquals(booking, response.getBody().getData());
    }

    @Test
    void getBooking_nonExistingId_throwsRuntimeException() {
        when(bookingService.getBooking(2)).thenThrow(new BookingNotFoundException(2));
        assertThrows(RuntimeException.class, () -> bookingController.getBooking(2));
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
    void addBooking_throwsRuntimeException() {
        doThrow(new RuntimeException("Test Exception")).when(bookingService).addBooking(any(Booking.class));
        assertThrows(RuntimeException.class, () -> bookingController.addBooking(new Booking()));
}

    @Test
    void deleteBooking_existingId_returnsOkResponse() {
        doNothing().when(bookingService).deleteBooking(1);

        ResponseEntity<TemplatesAPI<String>> response = bookingController.deleteBooking(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatus());
        assertEquals("Booking deleted", response.getBody().getMessage());
        assertEquals("OK", response.getBody().getData());
    }

    @Test
    void deleteBooking_nonExistingId_throwsRuntimeException() {
        doThrow(new RuntimeException("Test Exception")).when(bookingService).deleteBooking(2);
        assertThrows(RuntimeException.class, () -> bookingController.deleteBooking(2));
    }
}