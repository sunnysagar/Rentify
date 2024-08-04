package com.sunny.Rentify.repository;

import com.sunny.Rentify.model.UserEntity;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
=======
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
>>>>>>> cb952d6bbf9dacf8ba40ebde8bbec832d10c0e16
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String Email);
    Optional<UserEntity> findByEmailAndPassword(String email, String password);
<<<<<<< HEAD
    void deleteByEmail(String email);
//    Optional<UserEntity> findByRefreshToken(String refreshToken);
=======
>>>>>>> cb952d6bbf9dacf8ba40ebde8bbec832d10c0e16
}
