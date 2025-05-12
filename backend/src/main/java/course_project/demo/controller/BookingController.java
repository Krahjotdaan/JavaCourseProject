package course_project.demo.controller;

import course_project.demo.model.Booking;
import course_project.demo.model.TemplatesAPI;
import course_project.demo.model.Views;
import course_project.demo.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonView;

import java.time.LocalDate;
import java.util.List;

@RestController
@Tag(name = "Booking", description = "Booking API")
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    private static final Logger logger = LoggerFactory.getLogger(WorkspaceController.class);

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(summary = "Получение занятых интервалов времени рабочего пространства")
    @Transactional
    @GetMapping("/occupied")
    public ResponseEntity<List<Booking>> getOccupiedIntervals(@RequestParam String workspaceId, 
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        
            try {
                List<Booking> occupiedIntervals = bookingService.getOccupiedIntervals(workspaceId, date);
                return ResponseEntity.ok(occupiedIntervals);
            }
            catch (Exception e){
                logger.error("An unexpected error occurred while getting occupied intervals for workspace with id: ", workspaceId, e);
                throw e; 
            }
        }
    
    @Operation(summary = "Создание бронирования")
    @Transactional
    @PostMapping("/create")
    public ResponseEntity<TemplatesAPI<Booking>> createBooking(@RequestBody Booking booking) {
        try {
            Booking createdBooking = bookingService.createBooking(booking);
            return ResponseEntity.ok(new TemplatesAPI<>(200, "Booking created", createdBooking));
        } 
        catch (Exception e) {
            logger.error("An unexpected error occurred while creation booking", e);
            throw e;
        } 
    }

    @Operation(summary = "Получение бронирования по email")
    @Transactional
    @JsonView(Views.Internal.class)
    @GetMapping("/byEmail")
    public ResponseEntity<List<Booking>> getBookingsByEmail(@RequestParam String email) {
        try {
            List<Booking> bookings = bookingService.getBookingsByEmail(email);
            return ResponseEntity.ok(bookings);
        }
        catch (Exception e) {
            logger.error("An unexpected error occurred while getting bookings for user with email: ", email, e);
            throw e;
        }
    }

    @Operation(summary = "Удаление бронирования")
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