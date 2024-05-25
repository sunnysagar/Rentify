package com.sunny.Rentify.controller;


import com.sunny.Rentify.model.UserEntity;
import com.sunny.Rentify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rentify/users")
public class UserController {
    
   private final UserService userService;
   
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    // getmapping
    @GetMapping
    List<UserEntity> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUpUserEntity(@RequestBody UserEntity user){
        userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Successful");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUserEntity(@RequestBody UserEntity user){
        UserEntity authenticated = userService.validateTheUserRegistration(user);
        if(authenticated != null && userService.authenticateUser(user.getEmail(), user.getPassword()))
        {
            return ResponseEntity.ok("Logged in successfully");
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login credentials");
        }

    }
    
}
