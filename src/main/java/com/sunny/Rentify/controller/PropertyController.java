package com.sunny.Rentify.controller;


import com.sunny.Rentify.exception.PropertyNotFoundException;
import com.sunny.Rentify.model.PropertyEntity;
import com.sunny.Rentify.model.UserEntity;
import com.sunny.Rentify.repository.UserRepository;
import com.sunny.Rentify.service.PropertyService;
import com.sunny.Rentify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rentify/property")
public class PropertyController {

    private final PropertyService propertyService;
    private final UserService userService;

    @Autowired
    private SimpMessagingTemplate template;

    public PropertyController(PropertyService propertyService, UserService userService) {
        this.propertyService = propertyService;
        this.userService = userService;

    }

    //Read all property
    @GetMapping("/buyer")
    public ResponseEntity<?> getAllProperty()
    {
        List<PropertyEntity> properties = propertyService.getAllProperty();
        return ResponseEntity.ok(properties);
    }

    // get property by seller id
    @GetMapping("/seller")
    public ResponseEntity<?> getAllPropertyBySeller(Authentication authentication){
        String email = authentication.getName();
        UserEntity seller = userService.getUserByEmail(email);

        if (!"Seller".equalsIgnoreCase(seller.getUserType())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only sellers can view their properties.");
        }

        List<PropertyEntity> properties = propertyService.getPropertiesBySellerId(seller.getId());

        return ResponseEntity.ok(properties);
    }

    @PostMapping
    public ResponseEntity<?> addProperty(@RequestBody PropertyEntity property, Authentication authentication){
        String email = authentication.getName();
        UserEntity user = userService.getUserByEmail(email);

        if(!"Seller".equalsIgnoreCase(user.getUserType())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only sellers can add properties.");
        }

        property.setSeller(user);
        propertyService.addProperty(property);
        return ResponseEntity.status(HttpStatus.CREATED).body("Property added successfully!");
    }

    @PatchMapping("/update/{propertyId}")
    public ResponseEntity<?> updateProperty(@PathVariable("propertyId") long propertyId, @RequestBody Map<String, Object> updates, Authentication authentication)
    {
        String email = authentication.getName();
        UserEntity seller = userService.getUserByEmail(email);

        if(!"Seller".equalsIgnoreCase(seller.getUserType())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only seller can update the property details");
        }

        PropertyEntity existingProperty = propertyService.getPropertyById(propertyId);
        if(existingProperty == null || !existingProperty.getSeller().getId().equals(seller.getId())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized access or property not found.");
        }
//        property.setId(propertyId);
//        property.setSeller(seller);

        PropertyEntity updatedProperty = propertyService.updateProperty(propertyId, updates);

        // wrap message with metadata
        Map<String, Object> message = new HashMap<>();
        message.put("type", "PROPERTY_UPDATED");
        message.put("data", updatedProperty);
        try {
            template.convertAndSend("/topic/properties", message);
        }catch (Exception e){
            System.err.println("Error sending message: "+e.getMessage());
        }
        return ResponseEntity.ok(updatedProperty);
    }

    // Deleting book from DB
    @DeleteMapping("/delete/{propertyId}")
    public ResponseEntity<String> deleteBook(@PathVariable("propertyId") long propertyId){
        propertyService.deletePropertyById(propertyId);
        return ResponseEntity.ok("Book of ID - "+propertyId +" deleted successfully");
    }
}
