package com.sunny.Rentify.repository;

import com.sunny.Rentify.model.PropertyEntity;
import com.sunny.Rentify.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<PropertyEntity, Long> {

    // find the properties by seller
    List<PropertyEntity> findBySeller(UserEntity seller);

}
