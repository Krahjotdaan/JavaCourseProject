package course_project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import course_project.demo.model.Workspace;

public interface WorkspaceRepository extends JpaRepository<Workspace, String> {}
