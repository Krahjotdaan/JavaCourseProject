package course_project.demo.controllers;

import course_project.demo.controller.WorkspaceController;
import course_project.demo.exception.WorkspaceNotFoundException;
import course_project.demo.model.TemplatesAPI;
import course_project.demo.model.Workspace;
import course_project.demo.service.WorkspaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WebMvcTest(WorkspaceController.class)
public class WorkspaceControllerTest {

    @MockBean
    private WorkspaceService workspaceService;

    @Autowired
    private WorkspaceController workspaceController;

    private Workspace workspace;
    private String workspaceId;

    @BeforeEach
    void setUp() {
        workspaceId = "f123";
        workspace = new Workspace();
        workspace.setId(workspaceId);
        workspace.setType("test");
    }

    @Test
    void getWorkspace_existingId_returnsOkResponse() {
        when(workspaceService.getWorkspace(workspaceId)).thenReturn(workspace);

        ResponseEntity<TemplatesAPI<Workspace>> response = workspaceController.getUser(workspaceId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatus());
        assertEquals("Workspace found", response.getBody().getMessage());
        assertEquals(workspace, response.getBody().getData());
    }

    @Test
    void getWorkspace_nonExistingId_returnsNotFoundResponse() {
        String errorMessage = "Workspace not found with id: " + workspaceId;
        when(workspaceService.getWorkspace(workspaceId)).thenThrow(new WorkspaceNotFoundException(workspaceId));

        ResponseEntity<TemplatesAPI<Workspace>> response = workspaceController.getUser(workspaceId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void addWorkspace_validInput_returnsOkResponse() {
        when(workspaceService.addWorkspace(workspace)).thenReturn(workspace);

        ResponseEntity<TemplatesAPI<Workspace>> response = workspaceController.addWorkspace(workspace);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatus());
        assertEquals("Workspace added", response.getBody().getMessage());
        assertEquals(workspace, response.getBody().getData());
    }

    @Test
    void deleteWorkspace_existingId_returnsOkResponse() {
         when(workspaceService.getWorkspace(workspaceId)).thenReturn(workspace);

        ResponseEntity<TemplatesAPI<String>> response = workspaceController.deleteUser(workspaceId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatus());
        assertEquals("Workspace deleted", response.getBody().getMessage());
        assertEquals("OK", response.getBody().getData());

        verify(workspaceService, times(1)).deleteWorkspace(workspaceId);
    }

    @Test
    void deleteWorkspace_nonExistingId_returnsNotFoundResponse() {
        String errorMessage = "Workspace not found with id: " + workspaceId;
        when(workspaceService.getWorkspace(workspaceId)).thenThrow(new WorkspaceNotFoundException(workspaceId));

        ResponseEntity<TemplatesAPI<String>> response = workspaceController.deleteUser(workspaceId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertNull(response.getBody().getData());

        verify(workspaceService, never()).deleteWorkspace(workspaceId);
    }
}