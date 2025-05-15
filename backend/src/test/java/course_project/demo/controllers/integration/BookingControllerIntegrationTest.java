package course_project.integration.controller;

import course_project.demo.model.Booking;
import course_project.demo.model.User;
import course_project.demo.model.Workspace;
import course_project.demo.repository.BookingRepository;
import course_project.demo.repository.UserRepository;
import course_project.demo.repository.WorkspaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @BeforeEach
    void setUp() {
        bookingRepository.deleteAll();
        userRepository.deleteAll();
        workspaceRepository.deleteAll();
    }

    @Test
    void testCreateBooking() throws Exception {
        String userEmail = "test@example.com";
        String workspaceId = "workspace1";

        userRepository.save(new User(null, "Test User", Role.STUDENT, userEmail));
        workspaceRepository.save(new Workspace(workspaceId, Type.MEETING_ROOM));

        String bookingJson = String.format(
                "{\"userEmail\":\"%s\",\"workspaceId\":\"%s\",\"startTime\":\"%s\",\"endTime\":\"%s\"}",
                userEmail, workspaceId, LocalDateTime.now().plusHours(1).toString(), LocalDateTime.now().plusHours(2).toString()
        );

        mockMvc.perform(post("/api/bookings/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookingJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void testGetBookingsByEmail() throws Exception {
        String userEmail = "test@example.com";
        String workspaceId = "workspace1";

        userRepository.save(new User(null, "Test User", Role.STUDENT, userEmail));
        workspaceRepository.save(new Workspace(workspaceId, Type.MEETING_ROOM));

        Booking booking = new Booking(null, userEmail, workspaceId, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2));
        bookingRepository.save(booking);

        mockMvc.perform(get("/api/bookings/byEmail")
                        .param("email", userEmail))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists());
    }
}