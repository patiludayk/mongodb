package com.uday.mongodb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@Slf4j
public class MongodbApplication {

	public static void main(String[] args) {
		final ConfigurableApplicationContext run = SpringApplication.run(MongodbApplication.class, args);
	}
}
