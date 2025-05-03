package course_project.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import course_project.demo.model.TemplatesAPI;
import course_project.demo.model.Workspace;
import course_project.demo.service.WorkspaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Workspaace", description = "Workspace API")
@RequestMapping("/workspaces")
public class WorkspaceController {
    
    private final WorkspaceService workspaceService;

    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @Operation(summary = "Получение информации о рабочем пространстве")
    @GetMapping("/{id}")
    public ResponseEntity<TemplatesAPI<Workspace>> getUser(@PathVariable String id) {
        
        Workspace workspace = workspaceService.getWorkspace(id);

        if (workspace != null) {
            return ResponseEntity.ok(new TemplatesAPI<>(200, "Рабочее пространство найдено", workspace));
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new TemplatesAPI<>(404, "Рабочее пространство не найдено", null));
        }
    }

    @Operation(summary = "Добавление рабочего пространства")
    @PostMapping
    public ResponseEntity<TemplatesAPI<Workspace>> addWorkspace(@PathVariable String id, @Valid @RequestBody Workspace workspace) {
        
        Workspace newWorkspace = workspaceService.addWorkspace(id, workspace);  

        return ResponseEntity.ok(new TemplatesAPI<>(200, "Рабочее пространство добавлено", newWorkspace));
    }

    @Operation(summary = "Удаление рабочего пространства")
    @DeleteMapping("/{id}")
    public ResponseEntity<TemplatesAPI<String>> deleteUser(@PathVariable String id) {

        if (workspaceService.getWorkspace(id) != null) {
            workspaceService.deleteWorkspace(id);

            return ResponseEntity.ok(new TemplatesAPI<>(200, "Рабочее пространство удалено", "OK"));
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new TemplatesAPI<>(404, "Рабочее пространство не найдено", null));
        }
    }
}
