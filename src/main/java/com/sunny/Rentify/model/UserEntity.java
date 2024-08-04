package com.sunny.Rentify.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

   @NotNull(message = "Email is required")
    private String email;
   @NotNull(message = "Phone is required")
    private String phoneNumber;

    @NotBlank(message = "password is required")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
            message = "Password must contain at least 8 characters, including at least one digit, one lowercase letter," +
                    " one uppercase letter, one special character, and no whitespace.")
    private String password;

    private String userType;

<<<<<<< HEAD
//    private String refreshToken;

=======
>>>>>>> cb952d6bbf9dacf8ba40ebde8bbec832d10c0e16

    public UserEntity() {
    }

    public UserEntity(Long id, String firstName, String lastName, String email,
                      String phoneNumber, String password, String userType) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userType = userType;
<<<<<<< HEAD
//        this.refreshToken = refreshToken;
=======
>>>>>>> cb952d6bbf9dacf8ba40ebde8bbec832d10c0e16
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
<<<<<<< HEAD

//    public String getRefreshToken() {
//        return refreshToken;
//    }
//
//    public void setRefreshToken(String refreshToken) {
//        this.refreshToken = refreshToken;
//    }
=======
>>>>>>> cb952d6bbf9dacf8ba40ebde8bbec832d10c0e16
}

