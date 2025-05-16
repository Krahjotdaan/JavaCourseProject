package course_project.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookingNotFoundException extends RuntimeException {

    public BookingNotFoundException(Integer id) {
        super("Booking not found with id: " + id);
    }
}