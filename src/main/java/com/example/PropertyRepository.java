package com.example;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;

public class PropertyRepository {
    private final Map<String, Property> properties = new HashMap<>();

    public void save(Property property) {
        if (properties.containsKey(property.getId())) {
            throw new IllegalArgumentException("Property with ID " + property.getId() + " already exists.");
        }
        
        properties.put(property.getId(), property);
        
    }

    public void updateStatus(String id, PropertyStatus status) {
        Property property = properties.get(id);
        if (property == null) {
            throw new IllegalArgumentException("Property with ID " + id + " not found.");
        }
        property.setStatus(status);
    }

    public Optional<Property> findById(String id) {
        return Optional.ofNullable(properties.get(id));
    }

    public List<Property> findAll() {
        return List.copyOf(properties.values());
    }
}
