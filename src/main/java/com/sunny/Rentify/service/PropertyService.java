package com.sunny.Rentify.service;


import com.sunny.Rentify.model.PropertyEntity;
import com.sunny.Rentify.model.UserEntity;
import com.sunny.Rentify.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.*;
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
    public PropertyEntity addProperty(PropertyEntity property){

       return propertyRepository.save(property);
    }

    // get a property by its ID
    public PropertyEntity getPropertyById(Long id){
        return propertyRepository.findById(id).orElse(null);

    }

    // get all property
    public List<PropertyEntity> getAllProperty()
    {
        return propertyRepository.findAll();
    }

    // get all property by sellerId
    public List<PropertyEntity> getPropertiesBySellerId(Long sellerId) {
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
                        if(value instanceof Integer){
                            existingProperty.setArea(((Integer) value).doubleValue());  // convert the integer into double before setting it
                        } else if (value instanceof Double) {
                            existingProperty.setArea((Double) value);
                        }
                        break;
                    case "bedrooms":
                        existingProperty.setBedrooms((Integer) value);
                        break;
                    case "bathrooms":
                        existingProperty.setBathrooms((Integer) value);
                        break;
                    case "price":
                        if(value instanceof Integer){
                            existingProperty.setPrice(((Integer) value).doubleValue());  // convert the integer into double before setting it
                        } else if (value instanceof Double) {
                            existingProperty.setPrice((Double) value);
                        }
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

    }


    // deleting the property, when it is present in DB
    public void deletePropertyById(long propertyId){

        propertyRepository.deleteById(propertyId);
    }
}
