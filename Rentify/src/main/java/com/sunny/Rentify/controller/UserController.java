package com.sunny.Rentify.controller;


import com.sunny.Rentify.model.UserEntity;
import com.sunny.Rentify.service.UserService;
import com.sunny.Rentify.util.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/rentify/users")
public class UserController {

    
   private final UserService userService;
   private final JwtUtil jwtUtil;
   
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
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
            String token = jwtUtil.generateToken(user.getEmail());
            return ResponseEntity.ok(token);
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login credentials");
        }

    }

//    private String generateToken(UserEntity user){
//        return Jwts.builder()
//                .setSubject(user.getEmail())
//                .claim("role", user.getUserType())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) //1 day expiration
//                .signWith(SignatureAlgorithm.ES256, SECRET_KEY)
//                .compact();
//    }

    // Define JwtResponse class inside this file or as a separate class
    public static class JwtResponse {
        private String token;

        public JwtResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }
    
}
