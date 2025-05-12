package course_project.demo.service;

import org.springframework.dao.DataIntegrityViolationException;
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
        try {
            return workspaceRepository.save(workspace);
        } 
        catch (DataIntegrityViolationException e) {
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
        Workspace workspace = workspaceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Workspace not found"));
		workspace.setType(updatedWorkspace.getType());

        return workspaceRepository.save(workspace);
    }

    public void deleteWorkspace(String id) {
        try {
            workspaceRepository.deleteById(id);
        }
        catch (Exception e) {
            throw new EntityNotFoundException("Workspace not found");
        }
    }
}