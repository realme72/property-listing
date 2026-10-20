package com.example;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ShortlistService {
    private final Map<String, Set<String>> shortlists = new HashMap<>();
    private final PropertyManager propertyManager;
    
    public ShortlistService(PropertyManager propertyManager) {
        this.propertyManager = propertyManager;
    }

    public boolean shortlistProperty(String userId, String propertyId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID cannot be empty.");
        }

        if (propertyId == null || propertyId.isBlank()) {
            throw new IllegalArgumentException("Property ID cannot be empty.");
        }

        Property property = propertyManager.getPropertyById(propertyId);

        if (property.getStatus() != PropertyStatus.AVAILABLE) {
            return false;
        }

        Set<String> propertyIds = shortlists.computeIfAbsent(
            userId,
            key -> new HashSet<>()
        );

        return propertyIds.add(propertyId);
    }

    public boolean removeShortlistedProperty(String userId, String propertyId) {
        if (userId == null || userId.isBlank()) {
            return false;
        }

        if (propertyId == null || propertyId.isBlank()) {
            return false;
        }

        Set<String> userShortlist = shortlists.get(userId);

        if (userShortlist == null) {
            return false;
        }

        return userShortlist.remove(propertyId);
    }

    public Set<String> getShortlistedProperties(String userId){
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID cannot be empty.");
        }

        Set<String> userShortlist = shortlists.get(userId);
        if (userShortlist == null) {
            return new HashSet<>();
        }

        return new HashSet<>(userShortlist);
    }


}
