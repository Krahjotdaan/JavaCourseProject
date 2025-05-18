package course_project.demo.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import course_project.demo.model.Booking;
import course_project.demo.model.Workspace;
import course_project.demo.service.BookingService;
import course_project.demo.service.WorkspaceService;
import org.junit.jupiter.api.AfterEach;
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
    private ObjectMapper objectMapper;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private WorkspaceService workspaceService;

    private Workspace testWorkspace;
    private Booking testBooking;

    @BeforeEach
    void setUp() {
        testWorkspace = new Workspace();
        testWorkspace.setId("test-workspace");
        testWorkspace.setType(Workspace.Type.MEETING_ROOM);
        workspaceService.addWorkspace(testWorkspace);

        testBooking = new Booking();
        testBooking.setUserEmail("test@example.com");
        testBooking.setWorkspaceId(testWorkspace.getId());
        testBooking.setStartTime(LocalDateTime.now().plusHours(1));
        testBooking.setEndTime(LocalDateTime.now().plusHours(2));

    }

    @AfterEach
    void tearDown() {
        bookingService.deleteBooking(testBooking.getId());
        workspaceService.deleteWorkspace(testWorkspace.getId());
    }

    @Test
    void testGetOccupiedIntervals_Success() throws Exception {
        bookingService.createBooking(testBooking);

        mockMvc.perform(get("/bookings/occupied")
                        .param("workspaceId", testBooking.getWorkspaceId())
                        .param("date", testBooking.getStartTime().toLocalDate().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].workspaceId").value(testBooking.getWorkspaceId()));
    }

    @Test
    void testCreateBooking_Success() throws Exception {
        String bookingJson = objectMapper.writeValueAsString(testBooking);

        mockMvc.perform(post("/bookings/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookingJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userEmail").value(testBooking.getUserEmail()))
                .andExpect(jsonPath("$.data.workspaceId").value(testBooking.getWorkspaceId()));
    }

    @Test
    void testGetBookingsByEmail_Success() throws Exception {
        bookingService.createBooking(testBooking);

        mockMvc.perform(get("/bookings/byEmail")
                        .param("email", testBooking.getUserEmail()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userEmail").value(testBooking.getUserEmail()));
    }

    @Test
    void testDeleteBooking_Success() throws Exception {
        Booking savedBooking = bookingService.createBooking(testBooking);

        mockMvc.perform(delete("/bookings/{id}", savedBooking.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Booking deleted"));
    }
}