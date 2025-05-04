package course_project.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import course_project.demo.model.*;
import course_project.demo.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Booking", description = "Booking API")
@RequestMapping("/bookings")
public class BookingController {
    
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(summary = "Получение брони")
    @GetMapping("/{id}")
    public ResponseEntity<TemplatesAPI<Booking>> getBooking(@PathVariable Integer id) {
        
        Booking booking = bookingService.getBooking(id);

        if (booking != null) {
            return ResponseEntity.ok(new TemplatesAPI<>(200, "Бронь найдена", booking));
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new TemplatesAPI<>(404, "Бронь не найдена", null));
        }
    }

    @Operation(summary = "Создание брони")
    @PostMapping
    public ResponseEntity<TemplatesAPI<Booking>> addBooking(@PathVariable String workspaceId, @PathVariable Integer userId, @Valid @RequestBody Booking booking) {
        
        Booking newBooking = bookingService.addBooking(workspaceId, userId, booking);  

        return ResponseEntity.ok(new TemplatesAPI<>(200, "Бронь создана", newBooking));
    }

    @Operation(summary = "Удаление брони")
    @DeleteMapping("/{id}")
    public ResponseEntity<TemplatesAPI<String>> deleteBooking(@PathVariable Integer id) {

        if (bookingService.getBooking(id) != null) {
            bookingService.deleteBooking(id);

            return ResponseEntity.ok(new TemplatesAPI<>(200, "Бронь удалена", "OK"));
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new TemplatesAPI<>(404, "Бронь не найдена", null));
        }
    }
}
