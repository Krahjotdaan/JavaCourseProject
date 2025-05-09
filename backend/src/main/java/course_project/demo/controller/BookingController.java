package course_project.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import course_project.demo.model.*;
import course_project.demo.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@Tag(name = "Booking", description = "Booking API")
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(summary = "Получение брони")
    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<TemplatesAPI<Booking>> getBooking(@PathVariable Integer id) {
        try {
            Booking booking = bookingService.getBooking(id);
            return ResponseEntity.ok(new TemplatesAPI<>(200, "Booking found", booking));
        } 
        catch (Exception e) {
            logger.error("An unexpected error occurred while getting booking with id: {}", id, e);
            throw e; 
        }
    }

    @Operation(summary = "Создание брони")
    @Transactional
    @PostMapping
    public ResponseEntity<TemplatesAPI<Booking>> addBooking(@Valid @RequestBody Booking booking) {
        try {
            Booking newBooking = bookingService.addBooking(booking);
            return ResponseEntity.ok(new TemplatesAPI<>(200, "Booking created", newBooking));
        }  
        catch (Exception e) {
            logger.warn("An unexpected error occurred while adding booking: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Operation(summary = "Удаление брони")
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<TemplatesAPI<String>> deleteBooking(@PathVariable Integer id) {
        try {
            bookingService.deleteBooking(id);
            return ResponseEntity.ok(new TemplatesAPI<>(200, "Booking deleted", "OK"));
        } 
        catch (Exception e) {
            logger.error("An unexpected error occurred while deleting booking with id: {}", id, e);
            throw e; 
        }
    }
}