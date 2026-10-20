package com.example;

public class Address{
    private String state;
    private String city;
    private String locality;
    private String pincode;

    public Address(
        String locality,
        String city, 
        String state,
        String pincode
    ){
        this.locality = locality;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getPincode() {
        return pincode;
    }

    public String getLocality() {
        return locality;
    }

}