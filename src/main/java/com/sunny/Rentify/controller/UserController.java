package com.sunny.Rentify.controller;


import com.sunny.Rentify.model.AuthenticationResponse;
import com.sunny.Rentify.model.UserEntity;
import com.sunny.Rentify.repository.UserRepository;
import com.sunny.Rentify.service.UserService;
import com.sunny.Rentify.component.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rentify/users")
public class UserController {

    
   private final UserService userService;
   private final JwtUtil jwtUtil;
   private final UserRepository userRepository;

   @Autowired
   private AuthenticationManager authenticationManager;
   
    public UserController(UserService userService, JwtUtil jwtUtil, UserRepository userRepository) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }
    
    // getmapping
    @GetMapping("/all")
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        List<UserEntity> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // get current
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String token){
        try {
            //Extract token from the Authorization header
            token = token.substring(7);

            // get username (email) from the token
//            String email = jwtUtil.getUsername(token);

            // load user details using the email
            UserEntity user = userService.getUserFromToken(token);

            // Check if user is not found
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }
            return ResponseEntity.ok(user);

        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token or unauthorized access");
        }
//        token = token.substring(7);
//        UserEntity user = userService.getUserFromToken(token);
//        if(user != null)
//            return ResponseEntity.ok(user);
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found.");
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signupUser(@RequestBody UserEntity user){
        userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Successful");
    }


//    @PostMapping("/login")
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserEntity authenticationRequest) throws Exception {
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
//
//        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getEmail());
//        final String jwt = userService.generateTokenForUser(userDetails);
//
//        return ResponseEntity.ok(new AuthenticationResponse(jwt));
//    }

//    @PostMapping("/login")
//    public ResponseEntity<Map<String,String>> loginUser(@RequestBody UserEntity user){
//        UserDetails userDetails = userService.loadUserByUsername(user.getEmail());
//        UserEntity authenticated = userService.validateTheUserRegistration(user);
//        if(authenticated != null && userService.authenticatedUser(user.getEmail(), user.getPassword()))
//        {
//            String token = userService.generateTokenForUser(userDetails);
//            UserEntity loggedUser = userRepository.findByEmail(user.getEmail()).orElseThrow();
//            Map<String, String> response = new HashMap<>();
//            response.put("token", token);
//            response.put("userType", loggedUser.getUserType());
//            return ResponseEntity.ok(response);
//        }
//        else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        }
//    }

    //login 3
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody UserEntity user) {
        UserDetails userDetails = userService.loadUserByUsername(user.getEmail());
        UserEntity authenticated = userService.validateTheUserRegistration(user);
        if (authenticated != null && userService.authenticatedUser(user.getEmail(), user.getPassword())) {
            AuthenticationResponse authResponse = userService.authenticateAndGenerateTokens(user.getEmail(), user.getPassword());
            UserEntity loggedUser = userRepository.findByEmail(user.getEmail()).orElseThrow();
            Map<String, String> response = new HashMap<>();
            response.put("token", authResponse.getToken());
//            response.put("refreshToken", authResponse.getRefreshToken());
            response.put("userType", loggedUser.getUserType());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

//    // Refresh token
//    @PostMapping("/refresh")
//    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody Map<String, String> request) {
//        String refreshToken = request.get("refreshToken");
//        AuthenticationResponse authResponse = userService.refreshToken(refreshToken);
//        Map<String, String> response = new HashMap<>();
//        response.put("token", authResponse.getToken());
//        response.put("refreshToken", authResponse.getRefreshToken());
//        return ResponseEntity.ok(response);
//    }


    // update the current user details
    @PutMapping("/me")
    public ResponseEntity<?> updateCurrentUser(@RequestHeader("Authorization") String token, @RequestBody UserEntity user){
        token = token.substring(7);
        UserEntity updatedUser = userService.updateUser(token, user);
        return ResponseEntity.ok(updatedUser);
    }

    // delete current user
    @DeleteMapping("/me")
    public ResponseEntity<?> deleteCurrentUser(@RequestHeader("Authorization") String token){
        token = token.substring(7);
        userService.deleteUser(token);
        return ResponseEntity.ok("Account deleted successfully!");
    }
    
}
