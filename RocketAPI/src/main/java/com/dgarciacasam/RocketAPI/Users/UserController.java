package com.dgarciacasam.RocketAPI.Users;

import com.dgarciacasam.RocketAPI.Users.Model.User;
import org.springframework.security.core.Authentication;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.dgarciacasam.RocketAPI.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserRepository userRepository;
    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getUser(@PathVariable Integer id) throws  IOException{
        Optional<User> usuario = userRepository.findById(id);
        Map<String, Object> respuesta = new HashMap<>();
        if(usuario.isPresent()){
            User user = usuario.get();
            String imageBase64 = Utils.getProfileImages(id);
            user.setProfilePic(imageBase64);
            respuesta.put("data", user);
            return ResponseEntity.ok(respuesta);
        }else{
            return ResponseEntity.ok(respuesta);
        }
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody User user){
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("response", false);
        respuesta.put("message", "el usuario no ha podido crearse");
        try{
            Optional<User> emailExists = userRepository.findByEmail(user.getEmail());
            Optional<User> usernameExists = userRepository.findByName(user.getName());
            if(usernameExists != null && emailExists != null)  {
                    return ResponseEntity.ok(respuesta);
            }else{
                respuesta.put("response", true);
                respuesta.put("message", "el usuario se ha creado con asd");
                System.out.println(user.getEmail() + " - " + user.getName() + " - " + user.getPassword());
                User savedUser = userRepository.save(user);
                respuesta.put("user", savedUser);
                return ResponseEntity.ok(respuesta);
            }
        }catch(Exception ex){
            return ResponseEntity.ok(respuesta);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity updateUser(@PathVariable Integer id, @RequestBody User user){
        try {
            // Establecer el ID proporcionado en el objeto user
            user.setId(id);

            // Obtener el usuario existente de la base de datos
            Optional<User> existingUserOptional = userRepository.findById(id);

            // Verificar si el usuario existe
            if(existingUserOptional.isPresent()){
                User existingUser = existingUserOptional.get();

                // Verificar y actualizar el campo email si es proporcionado
                if(user.getEmail() != null && !user.getEmail().equals(existingUser.getEmail())){
                    existingUser.setEmail(user.getEmail());
                }

                // Verificar y actualizar el campo nombre si es proporcionado
                if(user.getName() != null && !user.getName().equals(existingUser.getName())){
                    existingUser.setName(user.getName());
                }

                // Verificar y actualizar el campo contraseña si es proporcionado
                if(user.getPassword() != null && !user.getPassword().equals(existingUser.getPassword())){
                    existingUser.setPassword(user.getPassword());
                }

                // Guardar los cambios en la base de datos
                userRepository.save(existingUser);
            }

            return ResponseEntity.ok().build();
        } catch(Exception ex){
            // Manejar cualquier excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id){
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getProfilePic/{id}")
    public ResponseEntity getProfilePic(@PathVariable String id) throws IOException {
        Path imagePath = Paths.get("RocketAPI","src","main","resources","static","images","profile", id + ".jpg");
        if(!Files.exists(imagePath)){
            imagePath = Paths.get("RocketAPI","src","main","resources","static","images","profile", 0 + ".jpg");
        }
        Resource profilePic = new UrlResource(imagePath.toUri());

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(profilePic);
    }

    @PostMapping("/setProfilePic/{id}")
    public ResponseEntity setProfilePic(@PathVariable String id, @RequestParam("image") MultipartFile file) throws IOException {
        byte[] imageBytes = file.getBytes();
        //Hay que comprobar que el archivo sea jpg o png.
        Path path = Paths.get("RocketAPI","src","main","resources","static","images","profile", id + ".jpg");
        Files.write(path, imageBytes);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String>  handleValidationException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }



}
