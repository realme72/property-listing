package com.example;
import java.util.List;
import java.math.BigDecimal;

public class PropertyManager {
    private final PropertyRepository repository;

    public PropertyManager(PropertyRepository repository) {
        this.repository = repository;
    }

    public Property createProperty(
        String title,
        BigDecimal price,
        int bedrooms,
        Address address,
        String ownerId
    ) {
        Property property = new Property(title, price, bedrooms, address, ownerId);
        repository.save(property);
        return property;
    }

    public Property getPropertyById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Property with ID " + id + " not found."));
    }

    public List<Property> getAllProperties() {
        return repository.findAll();
    }

    public Property markAsSold(String id) {
        Property property = getPropertyById(id);
        property.setStatus(PropertyStatus.SOLD);
        return property;
    }
    
}
