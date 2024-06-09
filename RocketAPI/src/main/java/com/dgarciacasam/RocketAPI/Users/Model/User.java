package com.dgarciacasam.RocketAPI.Users.Model;

import com.dgarciacasam.RocketAPI.Projects.Model.ProjectModel;
import com.dgarciacasam.RocketAPI.Tasks.Model.Task;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Entity
@Data
@Table(name="Users")
public class User implements UserDetails {

    @Valid

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    @Getter
    @Setter
    @Column(name="name")
    private String name;

    @NotBlank(message = "La contraseña es obligatoria")
    @Getter
    @Setter
    @Column(name="password")
    private String password;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    @Getter
    @Setter
    @Column(name="email")
    private String email;

    @Transient
    private String profilePic;

    @JsonIgnore
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<Task> tasks;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name="UsersProjects",
            joinColumns = @JoinColumn(name="userId"),
            inverseJoinColumns = @JoinColumn(name="projectId")
    )
    private List<ProjectModel> projects;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.name;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
