package course_project.demo.controllers;

import course_project.demo.controller.WorkspaceController;
import course_project.demo.model.TemplatesAPI;
import course_project.demo.model.Workspace;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WorkspaceControllerTest {

    @Mock
    private WorkspaceService workspaceService;

    @InjectMocks
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
        assertEquals("Рабочее пространство найдено", response.getBody().getMessage());
        assertEquals(workspace, response.getBody().getData());
    }

    @Test
    void getWorkspace_nonExistingId_returnsNotFoundResponse() {
        when(workspaceService.getWorkspace(workspaceId)).thenReturn(null);

        ResponseEntity<TemplatesAPI<Workspace>> response = workspaceController.getUser(workspaceId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().getStatus());
        assertEquals("Рабочее пространство не найдено", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void addWorkspace_validInput_returnsOkResponse() {
        when(workspaceService.addWorkspace(workspace)).thenReturn(workspace);

        ResponseEntity<TemplatesAPI<Workspace>> response = workspaceController.addWorkspace(workspace);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatus());
        assertEquals("Рабочее пространство добавлено", response.getBody().getMessage());
        assertEquals(workspace, response.getBody().getData());
    }

    @Test
    void deleteWorkspace_existingId_returnsOkResponse() {
        when(workspaceService.getWorkspace(workspaceId)).thenReturn(workspace);
        doNothing().when(workspaceService).deleteWorkspace(workspaceId);

        ResponseEntity<TemplatesAPI<String>> response = workspaceController.deleteUser(workspaceId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getStatus());
        assertEquals("Рабочее пространство удалено", response.getBody().getMessage());
        assertEquals("OK", response.getBody().getData());

        verify(workspaceService, times(1)).deleteWorkspace(workspaceId);
    }

    @Test
    void deleteWorkspace_nonExistingId_returnsNotFoundResponse() {
        when(workspaceService.getWorkspace(workspaceId)).thenReturn(null);

        ResponseEntity<TemplatesAPI<String>> response = workspaceController.deleteUser(workspaceId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().getStatus());
        assertEquals("Рабочее пространство не найдено", response.getBody().getMessage());
        assertNull(response.getBody().getData());

        verify(workspaceService, never()).deleteWorkspace(workspaceId);
    }
}