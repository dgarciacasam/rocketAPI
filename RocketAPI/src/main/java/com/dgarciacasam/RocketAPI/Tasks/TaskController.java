package com.dgarciacasam.RocketAPI.Tasks;

import com.dgarciacasam.RocketAPI.TaskUsers.Model.TaskUser;
import com.dgarciacasam.RocketAPI.TaskUsers.Model.TaskUserId;
import com.dgarciacasam.RocketAPI.TaskUsers.TaskUserRepository;
import com.dgarciacasam.RocketAPI.Tasks.Model.Task;
import com.dgarciacasam.RocketAPI.Users.Model.User;
import com.dgarciacasam.RocketAPI.Utils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task")
public class TaskController{
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskUserRepository taskUserRepository;


    @GetMapping
    public ResponseEntity<List<Task>> getTasks() throws IOException, URISyntaxException {
        List<Task> taskList = taskRepository.findAll();
        for(Task task: taskList){
            for(User user: task.getUsers()){
                user.setProfilePic(Utils.getProfileImages(user.getId()));
            }
        }
        return ResponseEntity.ok(taskList);
    }

    @GetMapping("getTasksByUser/{userId}")
    public ResponseEntity<List<Task>> getTasksByUser(@PathVariable Integer userId){
        return ResponseEntity.ok(taskRepository.getTasksByUser(userId));
    }

    @GetMapping("getTasksByProject/{userId}/{projectId}")
    public ResponseEntity<List<Task>> getTasksByUser(@PathVariable Integer userId, @PathVariable Integer projectId){

        return ResponseEntity.ok(taskRepository.getTasksByProject(userId, projectId));
    }



    @GetMapping("/{id}")
    public ResponseEntity<Optional<Task>> getTask(@PathVariable Integer id){
        return ResponseEntity.ok(taskRepository.findById(id));
    }

    @PostMapping("/{userId}")
    @Transactional
    public ResponseEntity createTask(@RequestBody Task task, @PathVariable Integer userId){
        try {
            // Guarda la nueva tarea
            Task newTask = taskRepository.save(task);
            System.out.println(newTask.getId());
            System.out.println(newTask.getColumnId());

            // Crea el ID compuesto para la relación
            TaskUserId taskUserId = new TaskUserId(newTask.getId(), userId);
            TaskUser newTaskUser = new TaskUser(taskUserId);

            taskUserRepository.save(newTaskUser);

            return ResponseEntity.ok(newTask.getId());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity updateTask(@PathVariable Integer id, @RequestBody Task task){
        try{
            task.setId(id);
            taskRepository.save(task);
            return ResponseEntity.ok().build();
        }catch(Exception ex){
            // Manejar cualquier excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/deleteTask/{id}")
    public ResponseEntity deleteTask(@PathVariable Integer id){
        try{
            taskRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
