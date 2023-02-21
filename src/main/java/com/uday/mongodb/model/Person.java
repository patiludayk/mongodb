package com.uday.mongodb.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("person") //annotation @Document to set the collection name that will be used by the model. If the collection doesn’t exist, MongoDB will create it.
public class Person {

    @Id
    private int id;
    private String username;
    private String name;
    private String surname;
    private String gender;
    private int age;
    private String email;
    private String mobile;
    private Address address;

}
