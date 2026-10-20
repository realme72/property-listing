
package com.example;

import java.math.BigDecimal;
import java.util.UUID;

public class Property {
    private final String id;
    private final String title;
    private final String ownerId;
    private final Address address;
    private final int bedrooms;
    private final BigDecimal price;

    private PropertyStatus status;

    public Property(
            String title,
            BigDecimal price,
            int bedrooms,
            Address address,
            String ownerId) {

        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.ownerId = ownerId;
        this.address = address;
        this.bedrooms = bedrooms;
        this.price = price;
        this.status = PropertyStatus.AVAILABLE;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public Address getAddress() {
        return address;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public PropertyStatus getStatus() {
        return status;
    }

    public void setStatus(PropertyStatus status) {
        this.status = status;
    }

    // We'll add a controlled status update method
    // when we implement PropertyManager.
}
