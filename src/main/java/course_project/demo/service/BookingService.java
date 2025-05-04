package course_project.demo.service;

import course_project.demo.dto.BookingDto;
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

    public Booking addBooking(BookingDto bookingDto) {

        Booking booking = new Booking();

        if (userRepository.existsById(bookingDto.getUserId())) {
            if (workspaceRepository.existsById(bookingDto.getWorkspaceId())) {
                booking.setUserId(bookingDto.getUserId());
		        booking.setWorkspaceId(bookingDto.getWorkspaceId());
                booking.setTime(bookingDto.getTime());
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