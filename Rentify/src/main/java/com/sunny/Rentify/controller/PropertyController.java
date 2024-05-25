package com.sunny.Rentify.controller;


import com.sunny.Rentify.exception.PropertyNotFoundException;
import com.sunny.Rentify.model.PropertyEntity;
import com.sunny.Rentify.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("/property")
@Validated
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    //Read all property
    @GetMapping()
    public List<PropertyEntity> getAllProperty()
    {
        return propertyService.getAllProperty();
    }

    @PostMapping
    public ResponseEntity<?> addProperty(@Valid @RequestBody PropertyEntity property){


        // create the book
        if(propertyService.createProperty(property)){
            return ResponseEntity.status(HttpStatus.CREATED).body("Property added successfully.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unsuccessfully");
    }

    @PutMapping("/update/{propertyId}")
    public ResponseEntity<?> updateProperty(@PathVariable("propertyId") long propertyId, @Valid @RequestBody PropertyEntity property)
    {
        if(propertyService.updateProperty(propertyId, property)){
            return ResponseEntity.status(HttpStatus.CREATED).body("Property"+ propertyId +" updated successfully.");

        }
        throw new PropertyNotFoundException("Property with ID " + propertyId + " not found.");

    }

    // Deleting book from DB
    @DeleteMapping("/delete/{propertyId}")
    public ResponseEntity<String> deleteBook(@PathVariable("propertyId") long propertyId){
        propertyService.deletePropertyById(propertyId);
        return ResponseEntity.ok("Book of ID - "+propertyId +" deleted successfully");
    }
}
