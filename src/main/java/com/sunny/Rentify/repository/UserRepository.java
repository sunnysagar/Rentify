package com.sunny.Rentify.repository;

import com.sunny.Rentify.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String Email);
    Optional<UserEntity> findByEmailAndPassword(String email, String password);
    void deleteByEmail(String email);
//    Optional<UserEntity> findByRefreshToken(String refreshToken);
}
