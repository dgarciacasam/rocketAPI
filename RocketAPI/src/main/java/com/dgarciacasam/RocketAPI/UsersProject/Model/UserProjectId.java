package com.dgarciacasam.RocketAPI.UsersProject.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserProjectId {
    @Column(name="userId")
    private Integer userId;
    @Column(name="projectId")
    private Integer projectId;

    public UserProjectId(){}
    public UserProjectId(Integer userId, Integer projectId){
        this.userId = userId;
        this.projectId = projectId;
    }
}
