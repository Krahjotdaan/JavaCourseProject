package course_project.demo.service;

import course_project.demo.model.Booking;
import course_project.demo.repository.*;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, WorkspaceRepository workspaceRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.workspaceRepository = workspaceRepository;
        this.userRepository = userRepository;
    }

    public Booking addBooking(String workspaceId, Integer userId, Booking booking) {
        booking.setUserId(userId);
		booking.setWorkspaceId(workspaceId);

        if (userRepository.existsById(userId)) {
            if (workspaceRepository.existsById(workspaceId)) {
                booking.setUserId(userId);
		        booking.setWorkspaceId(workspaceId);
            }
            else {
                throw new EntityNotFoundException("Рабочее пространство не найдено");
            }
        }
        else {
            throw new EntityNotFoundException("Пользователь не найден");
        }

        return bookingRepository.save(booking);
    }

    public Booking getBooking(Integer id) {
        return bookingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Бронь не найдена"));
    }

    public List<Booking> getBookingsByUserId(Integer userId) {
		if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("Пользователь не найден");
        }
        
        return bookingRepository.findByUserId(userId);
    }

    public void deleteBooking(Integer id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
		}
		else {
			throw new EntityNotFoundException("Бронь не найдена");
		}
    }
}