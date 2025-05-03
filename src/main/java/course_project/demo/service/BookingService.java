package course_project.demo.service;

import course_project.demo.model.Booking;
import course_project.demo.model.User;
import course_project.demo.model.Workspace;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class BookingService {
    
    private final Map<Integer, Booking> bookings = new HashMap<>();
    private Integer id = 1;

    public Booking addBooking(Workspace workspace, Booking booking, User user) {
        booking.setId(id);
        id++;
        bookings.put(booking.getId(), booking);
        workspace.getBookings().add(booking.getId());
        user.getBookings().add(booking.getId());

        return booking;
    }

    public Booking getBooking(Integer id) {
        return bookings.get(id);
    }

    public void deleteBooking(Integer id) {
        bookings.remove(id);
    }
}
