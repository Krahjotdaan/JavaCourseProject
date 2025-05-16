package course_project.demo.controllers;

import course_project.demo.controller.WorkspaceController;
import course_project.demo.exception.WorkspaceNotFoundException;
import course_project.demo.model.Workspace;
import course_project.demo.model.TemplatesAPI;
import course_project.demo.service.WorkspaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WorkspaceControllerTest {

    @Mock
    private WorkspaceService workspaceService;

    @InjectMocks
    private WorkspaceController workspaceController;

    private Workspace workspace;

    @BeforeEach
    void setUp() {
        workspace = new Workspace();
        workspace.setId("f123");
    }

    @Test
    void getBooking_existingId_returnsOkResponse() {
        when(workspaceService.getWorkspace("f123")).thenReturn(workspace);

        ResponseEntity<TemplatesAPI<Workspace>> response = workspaceController.getWorkspace("f123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatus());
        assertEquals("Workspace found", response.getBody().getMessage());
        assertEquals(workspace, response.getBody().getData());
    }

    @Test
    void getWorkspace_nonExistingId_throwsNotFound() {
        when(workspaceService.getWorkspace("f234")).thenThrow(new WorkspaceNotFoundException("f234"));
        assertThrows(RuntimeException.class, () -> workspaceController.getWorkspace("f234"));
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
    void addWorkspace_throwsRuntimeException() {
        doThrow(new RuntimeException("Test Exception")).when(workspaceService).addWorkspace(any(Workspace.class));
        assertThrows(RuntimeException.class, () -> workspaceController.addWorkspace(new Workspace()));
}

    @Test
    void deleteWorkspace_existingId_returnsOkResponse() {
        doNothing().when(workspaceService).deleteWorkspace("f123");

        ResponseEntity<TemplatesAPI<String>> response = workspaceController.deleteWorkspace("f123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatus());
        assertEquals("Workspace deleted", response.getBody().getMessage());
        assertEquals("OK", response.getBody().getData());
    }

    @Test
    void deleteWorkspace_nonExistingId_throwsRuntimeException() {
        doThrow(new RuntimeException("Test Exception")).when(workspaceService).deleteWorkspace("f234");
        assertThrows(RuntimeException.class, () -> workspaceController.deleteWorkspace("f234"));
    }
}