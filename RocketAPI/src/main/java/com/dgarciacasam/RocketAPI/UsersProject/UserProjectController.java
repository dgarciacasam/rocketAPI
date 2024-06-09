package com.dgarciacasam.RocketAPI.UsersProject;

import com.dgarciacasam.RocketAPI.UsersProject.Model.UserProject;
import com.dgarciacasam.RocketAPI.UsersProject.Model.UserProjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userProject")
public class UserProjectController {
    @Autowired
    public UserProjectRepository userProjectRepository;

    @PostMapping("/{userId}/{projectId}")
    public ResponseEntity createUserProject(@PathVariable Integer userId, @PathVariable Integer projectId){
        UserProjectId userProjectId = new UserProjectId(userId, projectId);
        UserProject userProject = new UserProject(userProjectId);
        userProjectRepository.save(userProject);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/{projectId}")
    public ResponseEntity deleteUserProject(@PathVariable Integer userId, @PathVariable Integer projectId){
        UserProjectId userProjectId = new UserProjectId(userId, projectId);
        UserProject userProject = new UserProject(userProjectId);
        userProjectRepository.delete(userProject);
        return ResponseEntity.ok().build();
    }
}
