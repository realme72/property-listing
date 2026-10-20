package com.example;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class App {
    public static void main(String[] args) {

        // 1. Create addresses
        Address address1 = new Address(
            "AECS Layout",
            "Bangalore",
            "Karnataka",
            "560037"
        );

        Address address2 = new Address(
            "Whitefield",
            "Bangalore",
            "Karnataka",
            "560066"
        );

        Address address3 = new Address(
            "Andheri",
            "Mumbai",
            "Maharashtra",
            "400053"
        );

        // 2. Set up repository and manager
        PropertyRepository repository = new PropertyRepository();
        PropertyManager propertyManager = new PropertyManager(repository);

        // 3. Create properties through PropertyManager
        Property property1 = propertyManager.createProperty(
            "3 BHK Apartment",
            new BigDecimal("7500000"),
            3,
            address1,
            "owner123"
        );

        Property property2 = propertyManager.createProperty(
            "2 BHK Apartment",
            new BigDecimal("5500000"),
            2,
            address2,
            "owner456"
        );

        Property property3 = propertyManager.createProperty(
            "3 BHK Flat",
            new BigDecimal("9500000"),
            3,
            address3,
            "owner789"
        );

        System.out.println("=== Property Details ===");
        printProperty(property1);
        printProperty(property2);
        printProperty(property3);

        System.out.println("\nTotal properties: "
            + propertyManager.getAllProperties().size());

        // 4. Test searching by city
        SearchService searchService = new SearchService(repository);

        SearchCriteria cityCriteria = new SearchCriteria();
        cityCriteria.setCity("Bangalore");

        List<Property> bangaloreProperties =
            searchService.search(cityCriteria);

        System.out.println("\n=== Search: Bangalore ===");
        printProperties(bangaloreProperties);

        // 5. Test price range
        SearchCriteria priceCriteria = new SearchCriteria();
        priceCriteria.setMinPrice(new BigDecimal("5000000"));
        priceCriteria.setMaxPrice(new BigDecimal("8000000"));

        List<Property> priceResults = searchService.search(priceCriteria);

        System.out.println("\n=== Search: Price 50L to 80L ===");
        printProperties(priceResults);

        // 6. Test bedroom filter
        SearchCriteria bedroomCriteria = new SearchCriteria();
        bedroomCriteria.setBedrooms(3);

        List<Property> bedroomResults =
            searchService.search(bedroomCriteria);

        System.out.println("\n=== Search: 3 Bedrooms ===");
        printProperties(bedroomResults);

        // 7. Test sorting by price, ascending
        SearchCriteria sortCriteria = new SearchCriteria();
        sortCriteria.setSortBy(SortBy.PRICE);
        sortCriteria.setSortOrder(SortOrder.ASC);

        List<Property> sortedResults = searchService.search(sortCriteria);

        System.out.println("\n=== All Available Properties: Price Ascending ===");
        printProperties(sortedResults);

        // 8. Test shortlisting
        ShortlistService shortlistService =
            new ShortlistService(propertyManager);

        System.out.println("\n=== Shortlist Test ===");

        System.out.println("First attempt: "
            + shortlistService.shortlistProperty("user101", property1.getId()));

        System.out.println("Duplicate attempt: "
            + shortlistService.shortlistProperty("user101", property1.getId()));

        Set<String> shortlist =
            shortlistService.getShortlistedProperties("user101");

        System.out.println("User101 shortlist: " + shortlist);

        // 9. Mark property1 as sold
        propertyManager.markAsSold(property1.getId());

        System.out.println("\n=== Sold Property Test ===");
        System.out.println("Updated status: " + property1.getStatus());

        System.out.println("Attempt to shortlist sold property: "
            + shortlistService.shortlistProperty("user202", property1.getId()));

        // 10. Verify sold properties don't appear in search
        SearchCriteria finalCriteria = new SearchCriteria();
        finalCriteria.setCity("Bangalore");

        List<Property> availableBangaloreProperties =
            searchService.search(finalCriteria);

        System.out.println("\n=== Available Properties in Bangalore ===");
        printProperties(availableBangaloreProperties);

        System.out.println("\nTotal properties in repository: "
            + repository.findAll().size());
    }

    private static void printProperty(Property property) {
        System.out.println(
            property.getTitle()
            + " | City: " + property.getAddress().getCity()
            + " | Locality: " + property.getAddress().getLocality()
            + " | Price: " + property.getPrice()
            + " | Bedrooms: " + property.getBedrooms()
            + " | Status: " + property.getStatus()
            + " | ID: " + property.getId()
        );
    }

    private static void printProperties(List<Property> properties) {
        if (properties.isEmpty()) {
            System.out.println("No matching properties found.");
            return;
        }

        for (Property property : properties) {
            printProperty(property);
        }

        System.out.println("Results: " + properties.size());
    }
}
