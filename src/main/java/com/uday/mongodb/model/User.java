package com.uday.mongodb.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

@Data
@Document("employee")
public class User {

    @Id
    private BigInteger id;  //no primitive type works for @Id see - https://stackoverflow.com/questions/29651147/spring-boot-mongo-e11000-duplicate-key
//    private ObjectId id;  //no primitive type works for @Id see - https://stackoverflow.com/questions/29651147/spring-boot-mongo-e11000-duplicate-key
    @Indexed(unique = true)
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
