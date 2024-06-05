package com.dgarciacasam.RocketAPI.UsersProject;

import com.dgarciacasam.RocketAPI.UsersProject.Model.UserProject;
import com.dgarciacasam.RocketAPI.UsersProject.Model.UserProjectId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProjectRepository extends JpaRepository<UserProject, UserProjectId> {
}
