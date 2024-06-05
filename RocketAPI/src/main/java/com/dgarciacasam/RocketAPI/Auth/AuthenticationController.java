package com.dgarciacasam.RocketAPI.Auth;

import com.dgarciacasam.RocketAPI.Services.AuthenticationService;
import com.dgarciacasam.RocketAPI.Services.JwtService;
import com.dgarciacasam.RocketAPI.Services.Models.LoginResponse;
import com.dgarciacasam.RocketAPI.Services.Models.LoginUserDto;
import com.dgarciacasam.RocketAPI.Services.Models.RegisterUserDto;
import com.dgarciacasam.RocketAPI.Users.Model.User;
import com.dgarciacasam.RocketAPI.UsersProject.Model.UserProject;
import com.dgarciacasam.RocketAPI.UsersProject.Model.UserProjectId;
import com.dgarciacasam.RocketAPI.UsersProject.UserProjectRepository;
import com.dgarciacasam.RocketAPI.Utils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    private final UserProjectRepository userProjectRepository;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, UserProjectRepository userProjectRepository) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userProjectRepository = userProjectRepository;
    }


    //Creamos el usuario, hacemos login y lo añadimos a la tabla de proyectos generales
    @PostMapping("/signup")
    public ResponseEntity register(@RequestBody RegisterUserDto registerUserDto, HttpServletResponse httpServletResponse) {
        User user = authenticationService.signup(registerUserDto);
        System.out.println(user.getId());
        if(user != null){
            LoginUserDto registeredUser = new LoginUserDto(user.getUsername(), registerUserDto.getPassword());
            User authenticatedUser = authenticationService.authenticate(registeredUser);

            //Enviamos la cookie que nos permitirá autenticarnos
            String jwtToken = jwtService.generateToken(authenticatedUser);
            Cookie cookie = new Cookie("jwt", jwtToken);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(jwtService.getExpirationTime()/ 1000);
            cookie.setPath("/");
            httpServletResponse.addCookie(cookie);

            //Añadimos al usuario a la tabla de proyectos
            UserProject userProject = new UserProject(new UserProjectId(user.getId(), 0));
            userProjectRepository.save(userProject);

            LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
            return ResponseEntity.ok(loginResponse);
        }
        return ResponseEntity.ok().build();
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto, HttpServletResponse httpServletResponse) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        //Creamos una cookie para settearle como httpOnly en el navegador
        Cookie cookie = new Cookie("jwt", jwtToken);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(jwtService.getExpirationTime()/ 1000);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser(HttpServletRequest httpServletRequest) throws IOException {
        System.out.println("Authentication called");
        Cookie cookie = WebUtils.getCookie(httpServletRequest, "jwt");
        if (cookie != null){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = (User) authentication.getPrincipal();
            String profilePic = Utils.getProfileImages(currentUser.getId());
            currentUser.setProfilePic(profilePic);
            return ResponseEntity.ok(currentUser);
        }else{
            return ResponseEntity.ok().build();
        }

    }

    @DeleteMapping("/logout")
    public ResponseEntity<Map<String, Boolean>> deleteCookie(HttpServletResponse response) {
        Cookie jwtCookie = new Cookie("jwt", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);
        response.addCookie(jwtCookie);
        Map<String, Boolean> responseBody = new HashMap<>();
        responseBody.put("ok", true);
        return ResponseEntity.ok(responseBody);
    }


}
