package com.dgarciacasam.RocketAPI.Projects;

import com.dgarciacasam.RocketAPI.Projects.Model.ProjectModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface ProjectRepository extends JpaRepository<ProjectModel, Integer> {
    @Query(value = "SELECT p.id, p.name, p.description, p.adminId FROM Projects p \n" +
            "JOIN UsersProjects up ON p.id = up.projectId\n" +
            "WHERE up.userId = :id", nativeQuery = true)
    public List<ProjectModel> getProjectsByUser(@Param("id") Integer id);

    @Query(value = "SELECT p.name FROM Projects p WHERE p.id = :projectId", nativeQuery = true)
    public String getProjectNameById(@Param("projectId") Integer projectId);
}
