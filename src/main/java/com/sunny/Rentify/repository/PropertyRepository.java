package com.sunny.Rentify.repository;

import com.sunny.Rentify.model.PropertyEntity;
<<<<<<< HEAD
import com.sunny.Rentify.model.UserEntity;
=======
>>>>>>> cb952d6bbf9dacf8ba40ebde8bbec832d10c0e16
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<PropertyEntity, Long> {

<<<<<<< HEAD
    // find the properties by seller
    List<PropertyEntity> findBySeller(UserEntity seller);

=======
    List<PropertyEntity> findBySellerId(Long sellerId);
>>>>>>> cb952d6bbf9dacf8ba40ebde8bbec832d10c0e16
}
