package com.uday.mongodb.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("employee")
public class User {

    @Id
    private int id;
    private String userId;
    private String  jobTitleName;
    private String firstName;
    private String lastName;
    private String preferredFullName;
    private String employeeCode;
    private String region;
    private String phoneNumber;
    private String emailAddress;
}
