package com.uday.mongodb.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("address")
public class Address {

    private String houseNo;
    private String building;
    private String street;
    private String pincode;

    private String city;
}
