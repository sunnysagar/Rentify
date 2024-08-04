package com.sunny.Rentify.service;


import com.sunny.Rentify.exception.DuplicateUserException;
import com.sunny.Rentify.model.AuthenticationResponse;
import com.sunny.Rentify.model.UserEntity;
import com.sunny.Rentify.repository.UserRepository;
import com.sunny.Rentify.component.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    // Get all Users

    public List<UserEntity> getAllUsers()
    {
        return userRepository.findAll();
    }

    // get current user from token
    public UserEntity getUserFromToken(String token){
        String email = jwtUtil.getUsername(token);
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    // get user by email
    public UserEntity getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void registerUser(UserEntity user)
    {
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new DuplicateUserException("Duplicate User");
        }

//        UserEntity newUser = new UserEntity();
//        newUser.setFirstName(user.getFirstName());
//        newUser.setLastName(user.getLastName());
//        newUser.setEmail(user.getEmail());
//        newUser.setPhoneNumber(user.getPhoneNumber());
//        newUser.setPassword(user.getPassword());
//        newUser.setUserType(user.getUserType());

        userRepository.save(user);
    }

    // update the current user
    public UserEntity updateUser(String token, UserEntity updatedUser){
        String email = jwtUtil.getUsername(token);
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        user.setPhoneNumber(updatedUser.getPhoneNumber());
        user.setPassword(updatedUser.getPassword());
        return userRepository.save(user);
    }

    // Authenticate user

    @Transactional
    public boolean authenticatedUser(String email, String password) {
        // Retrieve the user from the database using the username
        Optional<UserEntity> user = userRepository.findByEmail(email);

        return user.isPresent() && user.get().getPassword().equals(password);
    }

    public UserEntity validateTheUserRegistration(UserEntity user) {

        return userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()).orElse(null);

    }

    // Load user by username
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       UserEntity user = userRepository.findByEmail(email).orElseThrow(
               () -> new UsernameNotFoundException("User not found with email:" + email));
       return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
               .password(user.getPassword())
               .authorities(user.getUserType())
               .build();
    }

    // generate token for user
    public String generateTokenForUser(UserDetails userDetails){
        UserEntity user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: "+ userDetails.getUsername()));
        return jwtUtil.generateToken(userDetails.getUsername(), user.getUserType());
    }

    // deleting the current user
    @Transactional
    public void deleteUser(String token){
        String email = jwtUtil.getUsername(token);
        userRepository.deleteByEmail(email);
    }

    // Generate authentication response (token and refresh token)
    public AuthenticationResponse authenticateAndGenerateTokens(String email, String password) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        if (!user.getPassword().equals(password)) {
            throw new BadCredentialsException("Invalid password");
        }
        String token = jwtUtil.generateToken(user.getEmail(), user.getUserType());
//        String refreshToken = jwtUtil.generateRefreshToken();
//        user.setRefreshToken(refreshToken);
        userRepository.save(user);
        return new AuthenticationResponse(token);
    }

//    // Refresh token
//    public AuthenticationResponse refreshToken(String refreshToken) {
//        UserEntity user = userRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new UsernameNotFoundException("User not found with refresh token"));
//        if (jwtUtil.isTokenExpired(refreshToken)) {
//            throw new BadCredentialsException("Refresh token is expired");
//        }
//        String newToken = jwtUtil.generateToken(user.getEmail(), user.getUserType());
//        String newRefreshToken = jwtUtil.generateRefreshToken();
//        user.setRefreshToken(newRefreshToken);
//        userRepository.save(user);
//        return new AuthenticationResponse(newToken, newRefreshToken);
//    }
}

