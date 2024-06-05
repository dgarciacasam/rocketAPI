package com.dgarciacasam.RocketAPI.UsersProject.Model;

import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="UsersProjects")
public class UserProject {
    @EmbeddedId
    private UserProjectId userProjectId;

    public UserProject(){}
    public UserProject(UserProjectId userProjectId){
        this.userProjectId = userProjectId;
    }
}
