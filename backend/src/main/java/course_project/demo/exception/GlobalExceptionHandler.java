package course_project.demo.exception;

import course_project.demo.model.TemplatesAPI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<TemplatesAPI<Object>> handleBookingNotFoundException(BookingNotFoundException e) {
        TemplatesAPI<Object> body = new TemplatesAPI<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<TemplatesAPI<Object>> handleUserNotFoundException(UserNotFoundException e) {
        TemplatesAPI<Object> body = new TemplatesAPI<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

   @ExceptionHandler(WorkspaceNotFoundException.class)
    public ResponseEntity<TemplatesAPI<Object>> handleWorkspaceNotFoundException(WorkspaceNotFoundException e) {
        TemplatesAPI<Object> body = new TemplatesAPI<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<TemplatesAPI<Object>> handleGenericException(Exception e) {
        TemplatesAPI<Object> body = new TemplatesAPI<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred", null);
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}