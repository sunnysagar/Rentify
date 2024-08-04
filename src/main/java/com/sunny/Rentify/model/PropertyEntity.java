package com.sunny.Rentify.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "property")
public class PropertyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull(message = "Title is required")
    private String title;
    private String description;
    private String address;
    private double area;
    private int bedrooms;
    private int bathrooms;
    private double price;
    private String nearbyAmenities;
<<<<<<< HEAD
    private String propertyType;
    private String image;

    @NotNull(message = "sellerId is required")
    @ManyToOne @JoinColumn(name = "seller_id", nullable = false)
    private UserEntity seller;

=======

//    @NotNull(message = "sellerId is required")
//    @ManyToOne @JoinColumn(name = "sellerId")
//    private UserEntity seller;

    private long sellerId;
>>>>>>> cb952d6bbf9dacf8ba40ebde8bbec832d10c0e16

    public PropertyEntity() {
    }

<<<<<<< HEAD
    public PropertyEntity(long id, String title, String description, String address,
                          double area, int bedrooms, int bathrooms, double price,
                          String nearbyAmenities, String propertyType, String image, UserEntity seller) {
=======
    public PropertyEntity(long id, String title, String description,
                          String address, double area, int bedrooms, int bathrooms, double price, String nearbyAmenities, long sellerId) {
>>>>>>> cb952d6bbf9dacf8ba40ebde8bbec832d10c0e16
        this.id = id;
        this.title = title;
        this.description = description;
        this.address = address;
        this.area = area;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.price = price;
        this.nearbyAmenities = nearbyAmenities;
<<<<<<< HEAD
        this.propertyType = propertyType;
        this.image = image;
        this.seller = seller;
    }


=======
        this.sellerId = sellerId;
    }

>>>>>>> cb952d6bbf9dacf8ba40ebde8bbec832d10c0e16
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getNearbyAmenities() {
        return nearbyAmenities;
    }

    public void setNearbyAmenities(String nearbyAmenities) {
        this.nearbyAmenities = nearbyAmenities;
    }

<<<<<<< HEAD
    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public UserEntity getSeller() {
        return seller;
    }

    public void setSeller(UserEntity seller) {
        this.seller = seller;
=======
    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
>>>>>>> cb952d6bbf9dacf8ba40ebde8bbec832d10c0e16
    }
}
