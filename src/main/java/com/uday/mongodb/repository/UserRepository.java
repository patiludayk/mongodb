package com.uday.mongodb.repository;

import com.uday.mongodb.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The API implementation happens in the repository. It acts as a link between the model and the database, and has all the methods for CRUD operations.
 * MongoRepository is used for basic queries that involve all or many fields of the document.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String>/*, QuerydslPredicateExecutor<Person>*/ {

    /*
    This method, findPersonByUserName, requires a parameter for the query, i.e., the field to filter the query by.We specify this with the annotation @Query.
     */
    @Query("{userId:'?0'}")     //? is plaeholder for parameter, 0 is number of param.
    User findUserByUserId(String username);

    /*
    This method uses the gender field to get all the persons of a particular sex.
    We only want to project the person’s name and gender in the query response, so we set those fields to 1.
     */
    @Query(value = "{firstName:'?0'}", fields = "{'gender' : 1}")
    List<User> findAll(String category);

    /*
    We reuse the method count() as it is.
     */
    public long count();


    @Query("{ 'firstName' : ?0 }")
    List<User> findUsersByName(String firstName);

    @Query("{ 'age' : { $gt: ?0, $lt: ?1 } }")
    List<User> findUsersByAgeBetween(int ageGT, int ageLT);

    @Query("{ 'firstName' : { $regex: ?0 } }")
    List<User> findUsersByRegexpName(String regexp);

    List<User> findByFirstName(String firstName);

    /*List<Person> findByNameLikeOrderByAgeAsc(String name);

    List<Person> findByAgeBetween(int ageGT, int ageLT);

    List<Person> findByNameStartingWith(String regexp);

    List<User> findByNameEndingWith(String regexp);//whats issue with this no property with 'name' found! */

    @Query(value = "{}", fields = "{firstName : 1}")
    List<User> findNameAndId();

    @Query(value = "{}", fields = "{_id : 0}")
    List<User> findNameAndAgeExcludeId();
}
