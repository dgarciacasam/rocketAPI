package com.dgarciacasam.RocketAPI.Projects.Model;

import com.dgarciacasam.RocketAPI.Tasks.Model.Task;
import com.dgarciacasam.RocketAPI.Users.Model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Entity
@Data
@Table(name="Projects")
public class ProjectModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="name")
    private String name;
    @Column(name="description")
    private String description;
    @Column(name="adminId")
    private Integer adminId;

    @ManyToMany(mappedBy = "projects")
    private List<User> users;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

}
