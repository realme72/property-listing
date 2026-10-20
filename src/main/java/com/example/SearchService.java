package com.example;
import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.Comparator;
import java.util.stream.Collectors;

public class SearchService {
    private final PropertyRepository repository;

    public SearchService(PropertyRepository repository) {
        this.repository = repository;
    }

    public List<Property> search(SearchCriteria searchCriteria) {
        List<Predicate<Property>> predicates = new ArrayList<>();
        // Implementation for searching properties based on criteria
        String city = searchCriteria.getCity();
        String locality = searchCriteria.getLocality();
        String pincode = searchCriteria.getPincode();
        BigDecimal minPrice = searchCriteria.getMinPrice();
        BigDecimal maxPrice = searchCriteria.getMaxPrice();
        Integer bedrooms = searchCriteria.getBedrooms();

        predicates.add(property -> property.getStatus()==PropertyStatus.AVAILABLE);

        if (minPrice != null && maxPrice != null && minPrice.compareTo(maxPrice) > 0) {
            throw new IllegalArgumentException(
                "Minimum price cannot exceed maximum price."
            );
        }

        if (city != null && !city.isBlank()){
            predicates.add(property -> property.getAddress().getCity().equalsIgnoreCase(city));
        }

        if (locality != null && !locality.isBlank()){
            predicates.add(property -> property.getAddress().getLocality().equalsIgnoreCase(locality));
        }

        if (pincode != null && !pincode.isBlank()){
            predicates.add(property -> property.getAddress().getPincode().equalsIgnoreCase(pincode));
        }

        if (minPrice != null && minPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                "Minimum price must be greater than zero."
            );
        }

        if (minPrice != null && minPrice.compareTo(BigDecimal.ZERO) >= 0){
            predicates.add(property -> property.getPrice().compareTo(minPrice) >= 0);
        }

        if (bedrooms != null && bedrooms <= 0) {
            throw new IllegalArgumentException(
                "Bedrooms must be greater than zero."
            );
        }
        
        if (bedrooms != null) {
            predicates.add(property -> property.getBedrooms() == bedrooms);
        }

        if (maxPrice != null && maxPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
            "Maximum price cannot be negative."
            );
        }

        if (maxPrice != null && maxPrice.compareTo(BigDecimal.ZERO) >= 0){
            predicates.add(property -> property.getPrice().compareTo(maxPrice) <= 0);
        }
        

        Predicate<Property> combinedPredicate = predicates.stream().reduce(x -> true, Predicate::and);

        List<Property> results = repository.findAll()
        .stream()
        .filter(combinedPredicate)
        .collect(Collectors.toCollection(ArrayList::new));

        if(searchCriteria.getSortBy() != null ) {
            
            Comparator<Property> comparator = 
                switch (searchCriteria.getSortBy()) {
                    case PRICE -> Comparator.comparing(Property::getPrice);
                    case BEDROOMS -> Comparator.comparingInt(Property::getBedrooms);
                };

            if(searchCriteria.getSortOrder() == SortOrder.DESC) {
                comparator = comparator.reversed();
            }
            results.sort(comparator);
        }
        return results;
    }

}
