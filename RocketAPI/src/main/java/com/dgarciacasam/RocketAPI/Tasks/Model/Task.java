package com.dgarciacasam.RocketAPI.Tasks.Model;

import com.dgarciacasam.RocketAPI.Projects.Model.ProjectModel;
import com.dgarciacasam.RocketAPI.Users.Model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name="Tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="title")
    private String title;
    @Column(name="description")
    private String description;
    @Column(name="finishDate")
    private Date finishDate;



    @Column(name="columnId")
    private Integer columnId;

    @Column(name="projectId")
    private Integer projectId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "TasksUsers",
            joinColumns = @JoinColumn(name = "taskId"),
            inverseJoinColumns = @JoinColumn(name = "userId")
    )
    private List<User> users;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="projectId", nullable = false, updatable = false, insertable = false)
    private ProjectModel project;

    public Task(){}
    public Task(String title, String description, Integer columnId, Integer projectId){
        this.title = title;
        this.description = description;
        this.columnId = columnId;
        this.projectId = projectId;
    }
}
