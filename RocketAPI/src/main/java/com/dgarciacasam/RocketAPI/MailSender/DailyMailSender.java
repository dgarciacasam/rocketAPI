package com.dgarciacasam.RocketAPI.MailSender;

import com.dgarciacasam.RocketAPI.Projects.ProjectRepository;
import com.dgarciacasam.RocketAPI.Services.MailSenderService;
import com.dgarciacasam.RocketAPI.Tasks.Model.Task;
import com.dgarciacasam.RocketAPI.Tasks.TaskRepository;
import com.dgarciacasam.RocketAPI.Users.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@EnableScheduling
public class DailyMailSender {
    @Autowired
    MailSenderService mailSenderService;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Scheduled (cron = "0 0 10 * * ?")
    public void SendDailyEmail(){
        List<Task> taskList = taskRepository.getFinalishingTasks();
        Map<String, Map<String, Set<Task>>> userTasksMap = new HashMap<>();

        for (Task task : taskList) {
            for (User user : task.getUsers()) {
                String userEmail = user.getEmail();
                String projectName = projectRepository.getProjectNameById(task.getProjectId());
                userTasksMap
                        .computeIfAbsent(userEmail, k -> new HashMap<>())
                        .computeIfAbsent(projectName, k -> new HashSet<>())
                        .add(task);
            }
        }

        for (Map.Entry<String, Map<String, Set<Task>>> userEntry : userTasksMap.entrySet()) {
            String userEmail = userEntry.getKey();
            Map<String, Set<Task>> projectTasksMap = userEntry.getValue();

            String mailBody = "";
            for (Map.Entry<String, Set<Task>> projectEntry : projectTasksMap.entrySet()) {
                String projectName = projectEntry.getKey();
                Set<Task> projectTasks = projectEntry.getValue();
                mailBody += "Proyecto: " + projectName + "\n";

                for (Task task : projectTasks) {
                    mailBody += "\t" + task.getTitle();
                }
                mailBody += "\n";
            }
            mailSenderService.sendEmail(userEmail, "Tienes tareas sin finalizar", mailBody);
            System.out.println(mailBody);
        }
    }
}
