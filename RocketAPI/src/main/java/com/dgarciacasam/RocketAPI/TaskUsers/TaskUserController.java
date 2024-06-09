package com.dgarciacasam.RocketAPI.TaskUsers;

import com.dgarciacasam.RocketAPI.TaskUsers.Model.TaskUser;
import com.dgarciacasam.RocketAPI.TaskUsers.Model.TaskUserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/taskUser")
public class TaskUserController {
    @Autowired
    public TaskUserRepository taskUserRepository;

    @PostMapping("/{userId}/{taskId}")
    public ResponseEntity createTaskUser(@PathVariable Integer userId, @PathVariable Integer taskId){
        TaskUserId taskUserId = new TaskUserId(userId, taskId);
        TaskUser taskUser = new TaskUser(taskUserId);
        taskUserRepository.save(taskUser);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/{taskId}")
    public ResponseEntity deleteTaskUser(@PathVariable Integer userId, @PathVariable Integer taskId){
        TaskUserId taskUserId = new TaskUserId(userId, taskId);
        TaskUser taskUser = new TaskUser(taskUserId);
        taskUserRepository.delete(taskUser);
        return ResponseEntity.ok().build();
    }
}
