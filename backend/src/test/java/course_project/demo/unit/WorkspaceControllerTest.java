package course_project.demo.unit;

import course_project.demo.controller.WorkspaceController;
import course_project.demo.model.TemplatesAPI;
import course_project.demo.model.Workspace;
import course_project.demo.service.WorkspaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class WorkspaceControllerTest {

    @Mock
    private WorkspaceService workspaceService;

    @InjectMocks
    private WorkspaceController workspaceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getWorkspace_ExistingWorkspaceId_ReturnsOkResponse() {
        String workspaceId = "testWorkspace";
        Workspace expectedWorkspace = new Workspace();
        expectedWorkspace.setId(workspaceId);
        when(workspaceService.getWorkspace(workspaceId)).thenReturn(expectedWorkspace);

        ResponseEntity<TemplatesAPI<Workspace>> response = workspaceController.getWorkspace(workspaceId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(200, response.getBody().getStatus());
        assertEquals("Workspace found", response.getBody().getMessage());
        assertEquals(expectedWorkspace, response.getBody().getData());
    }

    @Test
    void getAllWorkspaces_ReturnsOkResponseWithWorkspaces() {
        List<Workspace> expectedWorkspaces = Arrays.asList(new Workspace(), new Workspace());
        when(workspaceService.getAllWorkspaces()).thenReturn(expectedWorkspaces);

        ResponseEntity<TemplatesAPI<List<Workspace>>> response = workspaceController.getAllWorkspaces();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(200, response.getBody().getStatus());
        assertEquals("Workspaces found", response.getBody().getMessage());
        assertEquals(expectedWorkspaces, response.getBody().getData());
    }

    @Test
    void addWorkspace_ValidWorkspace_ReturnsOkResponse() {
        Workspace workspace = new Workspace();
        workspace.setId("newWorkspace");
        when(workspaceService.addWorkspace(workspace)).thenReturn(workspace);

        ResponseEntity<TemplatesAPI<Workspace>> response = workspaceController.addWorkspace(workspace);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(200, response.getBody().getStatus());
        assertEquals("Workspace added", response.getBody().getMessage());
        assertEquals(workspace, response.getBody().getData());
    }

    @Test
    void addWorkspace_DuplicateWorkspaceId_ReturnsConflictResponse() {
        Workspace workspace = new Workspace();
        workspace.setId("existingWorkspace");
        when(workspaceService.addWorkspace(workspace)).thenThrow(new DuplicateKeyException("Duplicate key"));

        ResponseEntity<TemplatesAPI<Workspace>> response = workspaceController.addWorkspace(workspace);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(409, response.getBody().getStatus());
        assertEquals("Workspace already exists with id: existingWorkspace", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void deleteWorkspace_ExistingWorkspaceId_ReturnsOkResponse() {
        String workspaceId = "testWorkspace";

        ResponseEntity<TemplatesAPI<String>> response = workspaceController.deleteWorkspace(workspaceId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(200, response.getBody().getStatus());
        assertEquals("Workspace deleted", response.getBody().getMessage());
        assertEquals("OK", response.getBody().getData());
        verify(workspaceService, times(1)).deleteWorkspace(workspaceId);
    }
}