package com.dgarciacasam.RocketAPI.Projects;

import com.dgarciacasam.RocketAPI.Projects.Model.ProjectModel;
import com.dgarciacasam.RocketAPI.TaskUsers.Model.TaskUser;
import com.dgarciacasam.RocketAPI.TaskUsers.Model.TaskUserId;
import com.dgarciacasam.RocketAPI.TaskUsers.TaskUserRepository;
import com.dgarciacasam.RocketAPI.Tasks.Model.Task;
import com.dgarciacasam.RocketAPI.Tasks.TaskRepository;
import com.dgarciacasam.RocketAPI.Users.Model.User;
import com.dgarciacasam.RocketAPI.UsersProject.Model.UserProject;
import com.dgarciacasam.RocketAPI.UsersProject.Model.UserProjectId;
import com.dgarciacasam.RocketAPI.UsersProject.UserProjectRepository;
import com.dgarciacasam.RocketAPI.Utils;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UserProjectRepository userProjectRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskUserRepository taskUserRepository;

    @GetMapping("/getProjecstByUser/{id}")
    public ResponseEntity getProjecstByUser(@PathVariable  Integer id) throws IOException {
        List<ProjectModel> projectList = projectRepository.getProjectsByUser(id);
        for(ProjectModel project: projectList){
            for(Task task: project.getTasks()){
                for(User user: task.getUsers()){
                    user.setProfilePic(Utils.getProfileImages(user.getId()));
                }
            }
            for(User user: project.getUsers()){
                user.setProfilePic(Utils.getProfileImages(user.getId()));
            }
        }
        return ResponseEntity.ok(projectList);
    }

    @Transactional
    @PostMapping("/{userId}")
    public ResponseEntity createProject(@RequestBody ProjectModel project, @PathVariable Integer userId){
        try {
            //Creamos el proyecto
            ProjectModel newProject = projectRepository.save(project);

            //Añadimos el usuario al proyecto para que lo tenga disponible
            UserProjectId userProjectId = new UserProjectId(userId, newProject.getId());
            UserProject userProject = new UserProject(userProjectId);
            userProjectRepository.save(userProject);

            List<Task> taskList = new ArrayList<Task>();

            //Creamos tareas de prueba para que no aparezca el proyecto vacío
            Task taskResult = taskRepository.save(new Task("Nueva tarea", "Añade una descripción a la tarea", 1, newProject.getId()));
            taskUserRepository.save(new TaskUser(new TaskUserId(taskResult.getId(), userId)));
            taskList.add(taskResult);
            taskResult = taskRepository.save(new Task("Nueva tarea", "Añade una descripción a la tarea", 2, newProject.getId()));
            taskUserRepository.save(new TaskUser(new TaskUserId(taskResult.getId(), userId)));
            taskList.add(taskResult);
            taskResult = taskRepository.save(new Task("Nueva tarea", "Añade una descripción a la tarea", 3, newProject.getId()));
            taskUserRepository.save(new TaskUser(new TaskUserId(taskResult.getId(), userId)));
            taskList.add(taskResult);

            newProject.setTasks(taskList);
            return ResponseEntity.ok(newProject);
        }catch(Exception ex){
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PutMapping("/{projectId}")
    public ResponseEntity updateProject(@RequestBody ProjectModel projectModel, @PathVariable Integer projectId){
        projectModel.setId(projectId);
        ProjectModel newProject = projectRepository.save(projectModel);
        if(newProject != null){
            return ResponseEntity.ok(newProject);
        }
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<HashMap<String,Boolean>> deleteProject(@PathVariable Integer projectId){
        try{
            projectRepository.deleteById(projectId);
            return ResponseEntity.ok().build();
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.ok().build();
        }
    }

}
