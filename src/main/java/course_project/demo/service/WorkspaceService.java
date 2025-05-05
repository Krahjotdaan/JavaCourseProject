package course_project.demo.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import course_project.demo.model.Workspace;
import course_project.demo.repository.WorkspaceRepository;
import jakarta.persistence.EntityNotFoundException;


@Service
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    public WorkspaceService(WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    public Workspace addWorkspace(Workspace workspace) {

        if (!workspaceRepository.existsById(workspace.getId())) {
            return workspaceRepository.save(workspace);
        }
        else {
            throw new DuplicateKeyException("Workspace with this id already exists");
        }
        
    }

    public Workspace getWorkspace(String id) {
        return workspaceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Workspace not found"));
    }
	
	public Iterable<Workspace> getAllWorkspaces() {
		return workspaceRepository.findAll();
	}

    public Workspace updateWorkspace(String id, Workspace updatedWorkspace) {
        Workspace workspace = getWorkspace(id);
		workspace.setType(updatedWorkspace.getType());

        return workspaceRepository.save(workspace);
    }

    public void deleteWorkspace(String id) {
		if (workspaceRepository.existsById(id)) {
            workspaceRepository.deleteById(id);
		}
		else {
			throw new EntityNotFoundException("Workspace not found");
		}
    }
}