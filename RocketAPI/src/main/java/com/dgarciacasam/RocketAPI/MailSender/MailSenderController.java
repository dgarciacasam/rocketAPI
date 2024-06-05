package com.dgarciacasam.RocketAPI.MailSender;

import com.dgarciacasam.RocketAPI.Services.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailSenderController {

    @Autowired
    private MailSenderService mailSenderService;



    @GetMapping("/sendEmail")
    public ResponseEntity sendEmail(){
        mailSenderService.sendEmail("lacandadouwu@gmail.com", "Merequetengue", "HELOUU AMOOORRR ESTOY PROBANDO A ENVIAR EMAILS DESDE MI APLICASION JIJIJI TE AMOOO <3333");
        return ResponseEntity.ok().build();
    }
}
