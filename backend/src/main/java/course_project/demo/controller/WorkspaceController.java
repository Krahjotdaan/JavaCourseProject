package course_project.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import course_project.demo.model.TemplatesAPI;
import course_project.demo.model.Workspace;
import course_project.demo.service.WorkspaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

@RestController
@Tag(name = "Workspace", description = "Workspace API")
@RequestMapping("/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;
    private static final Logger logger = LoggerFactory.getLogger(WorkspaceController.class);

    @Autowired
    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @Operation(summary = "Получение информации о рабочем пространстве")
    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<TemplatesAPI<Workspace>> getWorkspace(@PathVariable String id) {
        try {
            Workspace workspace = workspaceService.getWorkspace(id);
            return ResponseEntity.ok(new TemplatesAPI<>(200, "Workspace found", workspace));
        } 
        catch (Exception e) {
            logger.error("An unexpected error occurred while getting workspace with id: {}", id, e);
            throw e;
        }
    }

    @Operation(summary = "Добавление рабочего пространства")
    @Transactional
    @PostMapping
    public ResponseEntity<TemplatesAPI<Workspace>> addWorkspace(@Valid @RequestBody Workspace workspace) {
        try {
            Workspace newWorkspace = workspaceService.addWorkspace(workspace);
            return ResponseEntity.ok(new TemplatesAPI<>(200, "Workspace added", newWorkspace));
        } 
        catch (DuplicateKeyException e) {
            String id = workspace.getId();
            logger.warn("Duplicate workspace id: {}", id, e);

            return ResponseEntity.status(HttpStatus.CONFLICT).body(new TemplatesAPI<>(409, "Workspace already exists with id: " + id, null));
        } 
        catch (Exception e) {
            logger.error("An unexpected error occurred while adding workspace: {}", e.getMessage(), e);
            throw e; 
        }
    }

    @Operation(summary = "Удаление рабочего пространства")
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<TemplatesAPI<String>> deleteWorkspace(@PathVariable String id) {
        try {
            workspaceService.deleteWorkspace(id);
            return ResponseEntity.ok(new TemplatesAPI<>(200, "Workspace deleted", "OK"));
        } 
        catch (Exception e) {
            logger.error("An unexpected error occurred while deleting workspace with id: {}", id, e);
            throw e;
        }
    }
}