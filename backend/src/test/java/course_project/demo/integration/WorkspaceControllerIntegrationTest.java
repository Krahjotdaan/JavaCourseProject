package course_project.demo.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import course_project.demo.model.Workspace;
import course_project.demo.service.WorkspaceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class WorkspaceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WorkspaceService workspaceService;

    private Workspace testWorkspace;

    @BeforeEach
    void setUp() {
        testWorkspace = new Workspace();
        testWorkspace.setId("test-workspace");
        testWorkspace.setType(Workspace.Type.MEETING_ROOM);
    }

    @AfterEach
    void tearDown() {
        workspaceService.deleteWorkspace(testWorkspace.getId());
    }

    @Test
    void testGetWorkspace_Success() throws Exception {
        workspaceService.addWorkspace(testWorkspace);

        mockMvc.perform(get("/workspaces/{id}", testWorkspace.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(testWorkspace.getId()))
                .andExpect(jsonPath("$.data.type").value(testWorkspace.getType().toString()));
    }

    @Test
    void testGetWorkspace_NotFound() throws Exception {
        mockMvc.perform(get("/workspaces/{id}", "non-existent-id"))
                .andExpect(status().is5xxServerError()); 
    }

    @Test
    void testGetAllWorkspaces_Success() throws Exception {
        Workspace workspace1 = new Workspace();
        workspace1.setId("test-workspace-all-1");
        workspace1.setType(Workspace.Type.MEETING_ROOM);
        workspaceService.addWorkspace(workspace1);

        Workspace workspace2 = new Workspace();
        workspace2.setId("test-workspace-all-2");
        workspace2.setType(Workspace.Type.CLASSROOM);
        workspaceService.addWorkspace(workspace2);

        mockMvc.perform(get("/workspaces"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.size()").value(2));

        workspaceService.deleteWorkspace("test-workspace-all-1");
        workspaceService.deleteWorkspace("test-workspace-all-2");
    }


    @Test
    void testAddWorkspace_Success() throws Exception {
        String workspaceJson = objectMapper.writeValueAsString(testWorkspace);

        mockMvc.perform(post("/workspaces")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(workspaceJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(testWorkspace.getId()))
                .andExpect(jsonPath("$.data.type").value(testWorkspace.getType().toString()));
    }


    @Test
    void testAddWorkspace_DuplicateId() throws Exception {
        workspaceService.addWorkspace(testWorkspace);

        mockMvc.perform(post("/workspaces")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testWorkspace)))
                .andExpect(status().is4xxClientError()); 
    }


    @Test
    void testDeleteWorkspace_Success() throws Exception {
        workspaceService.addWorkspace(testWorkspace);

        mockMvc.perform(delete("/workspaces/{id}", testWorkspace.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Workspace deleted"));

    }
}