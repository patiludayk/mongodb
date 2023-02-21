package com.uday.mongodb.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;
import java.util.Collections;

/**
 * we didn't need to define MongoTemplate bean in the previous configuration since it's already defined in AbstractMongoClientConfiguration.
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.uday.mongodb.repository")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${database.url}")
    private String databaseUrl;

    @Value("${databaseName:userdb}")
    private String databaseName;

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(databaseUrl);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyConnectionString(connectionString).build();

        return MongoClients.create(mongoClientSettings);
    }

    @Override
    public Collection getMappingBasePackages() {
        return Collections.singleton("com.uday");
    }
}

//Note that we didn't need to define MongoTemplate bean in the previous configuration since it's already defined in AbstractMongoClientConfiguration.
//We can also use our configuration from scratch without extending AbstractMongoClientConfiguration:
//refer https://www.baeldung.com/spring-data-mongodb-tutorial - section 5 for MongoTemplate implementation.
/*@Configuration
@EnableMongoRepositories(basePackages = "com.uday.mongodb.repository")
public class SimpleMongoConfig {

    @Bean
    public MongoClient mongo() {
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/mongodbhelloworld");
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyConnectionString(connectionString).build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), "mongodbhelloworld");
    }
}*/
