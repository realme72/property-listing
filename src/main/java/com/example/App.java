package com.example;
import java.util.List;

public class App {
    public static void main(String[] args){
        Address address = new Address(
            "AECS Layout",
            "Bangalore",
            "Karnataka",
            "560037"
        );

        Property property = new Property(
            "3 BHK Apartment",
            new java.math.BigDecimal("7500000"),
            3,
            address,
            "owner123"
        );

        System.out.println("State: " + address.getState());
        System.out.println("City: " + address.getCity());
        System.out.println("Locality: " + address.getLocality());
        System.out.println("Pincode: " + address.getPincode());

        PropertyRepository repository = new PropertyRepository();
        repository.save(property);

        Property found = repository.findById(property.getId()).orElseThrow(
            () -> new IllegalStateException("Property not found")
        );
        System.out.println("Property ID: " + found.getId());
        System.out.println("Property Title: " + found.getTitle());
        System.out.println("Property Price: " + found.getPrice());
        System.out.println("Property Status: " + found.getStatus());

        repository.updateStatus(property.getId(), PropertyStatus.SOLD);
        System.out.println("Property Status: " + found.getStatus());

        // Retrieve all properties.
        List<Property> allProperties = repository.findAll();

        System.out.println("Total properties: " + allProperties.size());
    }
}
