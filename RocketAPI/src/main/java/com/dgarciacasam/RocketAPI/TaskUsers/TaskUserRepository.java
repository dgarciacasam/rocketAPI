package com.dgarciacasam.RocketAPI.TaskUsers;

import com.dgarciacasam.RocketAPI.TaskUsers.Model.TaskUser;
import com.dgarciacasam.RocketAPI.TaskUsers.Model.TaskUserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskUserRepository extends JpaRepository<TaskUser, TaskUserId> {
}
