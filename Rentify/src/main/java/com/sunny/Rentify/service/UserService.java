package com.sunny.Rentify.service;


import com.sunny.Rentify.exception.DuplicateUserException;
import com.sunny.Rentify.model.UserEntity;
import com.sunny.Rentify.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Get Users

    public List<UserEntity> getAllUsers()
    {
        return userRepository.findAll();
    }

    public void registerUser(UserEntity user)
    {
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new DuplicateUserException("Duplicate User");
        }

        UserEntity newUser = new UserEntity();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setPassword(user.getPassword());
        newUser.setUserType(user.getUserType());

        userRepository.save(newUser);
    }

    public boolean authenticateUser(String email, String password) {
        // Retrieve the user from the database using the username
        Optional<UserEntity> user = userRepository.findByEmail(email);

        // Check if the user exists and if the provided password matches the stored password
        UserEntity correctUser = user.get();
        return correctUser.getPassword().equals(password);
    }

    public UserEntity validateTheUserRegistration(UserEntity user) {

        return userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()).orElse(null);

    }
}
