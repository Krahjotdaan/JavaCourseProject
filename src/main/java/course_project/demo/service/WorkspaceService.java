package course_project.demo.service;

import org.springframework.stereotype.Service;

import course_project.demo.dto.WorkspaceDto;
import course_project.demo.model.Workspace;
import course_project.demo.repository.WorkspaceRepository;
import jakarta.persistence.EntityNotFoundException;


@Service
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    public WorkspaceService(WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    public Workspace addWorkspace(WorkspaceDto workspaceDto) {
        Workspace newWorkspace = new Workspace();

        newWorkspace.setId(workspaceDto.getId());
        newWorkspace.setType(workspaceDto.getType());

        return workspaceRepository.save(newWorkspace);
    }

    public Workspace getWorkspace(String id) {
        return workspaceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Рабочее пространство не найдено"));
    }
	
	public Iterable<Workspace> getAllWorkspaces() {
		return workspaceRepository.findAll();
	}

    public Workspace updateWorkspace(String id, Workspace updatedWorkspace) {
        Workspace workspace = workspaceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Рабочее пространство не найдено"));
		workspace.setType(updatedWorkspace.getType());

        return workspaceRepository.save(workspace);
    }

    public void deleteWorkspace(String id) {
		if (workspaceRepository.existsById(id)) {
            workspaceRepository.deleteById(id);
		}
		else {
			throw new EntityNotFoundException("Рабочее пространство не найдено");
		}
    }
}