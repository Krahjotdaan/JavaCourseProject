package course_project.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import course_project.demo.model.Workspace;

@Service
public class WorkspaceService {
    
    private final Map<String, Workspace> workspaces = new HashMap<>();

    public Workspace addWorkspace(String id, Workspace workspace) {
        if (!workspaces.containsKey(id)) {
            workspace.setId(id);
            workspaces.put(workspace.getId(), workspace);

            return workspace;
        }
        else {
            return workspaces.get(id);
        }
    }

    public Workspace getWorkspace(String id) {
        return workspaces.get(id);
    }

    public void deleteWorkspace(String id) {
        workspaces.remove(id);
    }
}