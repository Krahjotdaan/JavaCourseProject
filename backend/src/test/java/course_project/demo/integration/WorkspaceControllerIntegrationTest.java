package course_project.integration.controller;

import course_project.demo.model.Workspace;
import course_project.demo.repository.WorkspaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WorkspaceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @BeforeEach
    void setUp() {
        workspaceRepository.deleteAll();
    }

    @Test
    void testCreateWorkspace() throws Exception {
        String workspaceJson = "{\"id\":\"workspace1\",\"type\":\"MEETING_ROOM\"}";

        mockMvc.perform(post("/api/workspaces/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(workspaceJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void testGetWorkspace() throws Exception {
        Workspace workspace = new Workspace("workspace1", Type.MEETING_ROOM);
        workspaceRepository.save(workspace);

        mockMvc.perform(get("/api/workspaces/{id}", workspace.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("MEETING_ROOM"));
    }
}