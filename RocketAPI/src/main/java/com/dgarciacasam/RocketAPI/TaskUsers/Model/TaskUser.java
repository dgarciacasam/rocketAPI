package com.dgarciacasam.RocketAPI.TaskUsers.Model;

import com.dgarciacasam.RocketAPI.Tasks.Model.Task;
import com.dgarciacasam.RocketAPI.Users.Model.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="TasksUsers")
public class TaskUser {
    @EmbeddedId
    private TaskUserId id;

    // Constructors, getters, and setters
    public TaskUser() {}

    public TaskUser(TaskUserId taskUserId) {
        this.id = taskUserId;
    }
}

