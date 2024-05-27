package com.sunny.Rentify.service;


import com.sunny.Rentify.model.PropertyEntity;
import com.sunny.Rentify.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }
    
    
    // Add property
    public boolean createProperty(PropertyEntity property){

        propertyRepository.save(property);

        return true;
    }

    // get all property
    public List<PropertyEntity> getAllProperty()
    {
        return propertyRepository.findAll();
    }

    // get all property by sellerId
    public List<PropertyEntity> getPropertiesBySellerId(Long sellerId) {
        return propertyRepository.findBySellerId(sellerId);
    }

//    // get all non-rented books or available books
//    public List<String> getNonRentedProperty() {
//        List<PropertyEntity> allProperty = propertyRepository.findAll();
//        List<PropertyEntity> rentedBooks = rentalRepository.findByReturnDateNull().stream().map(Rental::getBook).toList();
//        List<PropertyEntity> nonRentedBooks = allBooks.stream().filter(book -> !rentedBooks.contains(book)).toList();
//        return nonRentedBooks.stream().map(Book::getTitle).collect(Collectors.toList());
//    }

    public boolean updateProperty(long propertyId, PropertyEntity updateProperty){
        // check property is present or not
        Optional<PropertyEntity> propertyPresent = propertyRepository.findById(propertyId);

        if(propertyPresent.isPresent()){
            // get the existing property
            PropertyEntity existingProperty = propertyPresent.get();

            // Now update the existing property
            existingProperty.setTitle(updateProperty.getTitle());
            existingProperty.setDescription(updateProperty.getDescription());
            existingProperty.setAddress(updateProperty.getAddress());

            existingProperty.setArea(Double.parseDouble(String.valueOf(updateProperty.getArea())));
            existingProperty.setBedrooms(Integer.parseInt(String.valueOf(updateProperty.getBedrooms())));
            existingProperty.setBathrooms(Integer.parseInt(String.valueOf(updateProperty.getBathrooms())));
            existingProperty.setPrice(Double.parseDouble(String.valueOf(updateProperty.getPrice())));

            existingProperty.setNearbyAmenities(updateProperty.getNearbyAmenities());

            // save the updated one
            propertyRepository.save(existingProperty);

            return true;
        }
        else
            return false;
    }


    // deleting the property, when it is present in DB
    public void deletePropertyById(long propertyId){

        propertyRepository.deleteById(propertyId);
    }
}
