package com.uday.mongodb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@Slf4j
public class MongodbApplication {

	public static void main(String[] args) {
		final ConfigurableApplicationContext run = SpringApplication.run(MongodbApplication.class, args);
	}
}
