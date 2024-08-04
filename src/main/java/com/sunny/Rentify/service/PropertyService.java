package com.sunny.Rentify.service;


import com.sunny.Rentify.model.PropertyEntity;
<<<<<<< HEAD
import com.sunny.Rentify.model.UserEntity;
=======
>>>>>>> cb952d6bbf9dacf8ba40ebde8bbec832d10c0e16
import com.sunny.Rentify.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
<<<<<<< HEAD
import java.util.*;
=======
import java.util.List;
import java.util.Optional;
>>>>>>> cb952d6bbf9dacf8ba40ebde8bbec832d10c0e16
import java.util.stream.Collectors;

@Service
public class PropertyService {

    PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }
    
    
    // Add property
<<<<<<< HEAD
    public PropertyEntity addProperty(PropertyEntity property){

       return propertyRepository.save(property);
    }

    // get a property by its ID
    public PropertyEntity getPropertyById(Long id){
        return propertyRepository.findById(id).orElse(null);
=======
    public boolean createProperty(PropertyEntity property){

        propertyRepository.save(property);

        return true;
>>>>>>> cb952d6bbf9dacf8ba40ebde8bbec832d10c0e16
    }

    // get all property
    public List<PropertyEntity> getAllProperty()
    {
        return propertyRepository.findAll();
    }

    // get all property by sellerId
    public List<PropertyEntity> getPropertiesBySellerId(Long sellerId) {
<<<<<<< HEAD
        UserEntity seller = new UserEntity();
        seller.setId(sellerId);
        return propertyRepository.findBySeller(seller);
    }

    public PropertyEntity updateProperty(long propertyId, Map<String, Object>updates){

        Optional<PropertyEntity> optionalProperty = propertyRepository.findById(propertyId);

        if (optionalProperty.isPresent()) {
            PropertyEntity existingProperty = optionalProperty.get();

            // Iterate through the updates map and set the corresponding fields
            updates.forEach((key, value) -> {
                switch (key) {
                    case "title":
                        existingProperty.setTitle((String) value);
                        break;
                    case "description":
                        existingProperty.setDescription((String) value);
                        break;
                    case "address":
                        existingProperty.setAddress((String) value);
                        break;
                    case "area":
                        existingProperty.setArea((Double) value);
                        break;
                    case "bedrooms":
                        existingProperty.setBedrooms((Integer) value);
                        break;
                    case "bathrooms":
                        existingProperty.setBathrooms((Integer) value);
                        break;
                    case "price":
                        existingProperty.setPrice((Double) value);
                        break;
                    case "nearbyAmenities":
                        existingProperty.setNearbyAmenities((String) value);
                        break;
                    case "propertyType":
                        existingProperty.setPropertyType((String) value);
                        break;
                    case "image":
                        existingProperty.setImage((String) value);
                        break;
                    // Handle other fields similarly
                }
            });

            // Save the updated property
            return propertyRepository.save(existingProperty);
        } else {
            throw new NoSuchElementException("Property not found");
        }

//        if(propertyPresent.isPresent()){
//
//            // Now update the existing property
//            existingProperty.setTitle(updateProperty.getTitle());
//            existingProperty.setDescription(updateProperty.getDescription());
//            existingProperty.setAddress(updateProperty.getAddress());
//
//            existingProperty.setArea(Double.parseDouble(String.valueOf(updateProperty.getArea())));
//            existingProperty.setBedrooms(Integer.parseInt(String.valueOf(updateProperty.getBedrooms())));
//            existingProperty.setBathrooms(Integer.parseInt(String.valueOf(updateProperty.getBathrooms())));
//            existingProperty.setPrice(Double.parseDouble(String.valueOf(updateProperty.getPrice())));
//
//            existingProperty.setNearbyAmenities(updateProperty.getNearbyAmenities());
//            existingProperty.setPropertyType(updateProperty.getPropertyType());
//            existingProperty.setImage(updateProperty.getImage());
//
//            // save the updated one
//            return propertyRepository.save(existingProperty);
//
//        }

//        return propertyRepository.save(existingProperty);

=======
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
>>>>>>> cb952d6bbf9dacf8ba40ebde8bbec832d10c0e16
    }


    // deleting the property, when it is present in DB
    public void deletePropertyById(long propertyId){

        propertyRepository.deleteById(propertyId);
    }
}
