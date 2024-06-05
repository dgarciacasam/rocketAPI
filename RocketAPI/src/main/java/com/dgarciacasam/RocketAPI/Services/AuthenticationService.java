package com.dgarciacasam.RocketAPI.Services;

import com.dgarciacasam.RocketAPI.Services.Models.LoginUserDto;
import com.dgarciacasam.RocketAPI.Services.Models.RegisterUserDto;
import com.dgarciacasam.RocketAPI.Users.UserRepository;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.dgarciacasam.RocketAPI.Users.Model.User;

import java.util.ArrayList;
import java.util.List;

@Service
    public class AuthenticationService {
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final AuthenticationManager authenticationManager;

        public AuthenticationService(
                UserRepository userRepository,
                AuthenticationManager authenticationManager,
                PasswordEncoder passwordEncoder
        ) {
            this.authenticationManager = authenticationManager;
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
        }

        public User signup(RegisterUserDto input) {
            var user = new User();
            user.setEmail(input.getEmail());
            user.setPassword(passwordEncoder.encode(input.getPassword()));
            user.setName(input.getUsername());
            return userRepository.save(user);
        }

        public User authenticate(LoginUserDto input) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getUsername(),
                            input.getPassword()
                    )
            );
            User usuario =  userRepository.findByName(input.getUsername()).orElseThrow();
            return usuario;
        }

        public List<User> allUsers() {
            List<User> users = new ArrayList<>();

            userRepository.findAll().forEach(users::add);

            return users;
        }
    }
