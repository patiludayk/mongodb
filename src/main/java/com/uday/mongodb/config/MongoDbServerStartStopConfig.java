package com.uday.mongodb.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@Slf4j
public class MongoDbServerStartStopConfig implements ServletContextListener {

    @PostConstruct
    public void startMongoDb() {
        try {
            log.info("starting mongodb");
            final Process exec = Runtime.getRuntime().exec("mongod --dbpath /usr/local/var/mongodb --logpath /usr/local/var/log/mongodb/mongo.log");
            final InputStream output = exec.getInputStream();
            final Stream<String> lines = new BufferedReader(new InputStreamReader(output)).lines();
            TimeUnit.SECONDS.sleep(3);
            new Thread(() -> {
                lines.forEach(log::info);
            }, "mongodb-starter-thread").start();
            log.info("mongodb started successfully.");
        } catch (IOException e) {
            log.error("error starting mongodb server. {}", e);
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            log.error("sleep interrupted after mongodb start");
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    public void stopMongoDb() {
        log.info("*******************************");
        try {
            final Process exec = Runtime.getRuntime().exec("lsof -i -n -P | grep \"mongod\"");
            final InputStream output = exec.getInputStream();
            final Stream<String> lines = new BufferedReader(new InputStreamReader(output)).lines();
            lines.collect(Collectors.toList());
        } catch (IOException e) {
            log.error("error stopping mongodb server. {}", e);
            throw new RuntimeException(e);
        }
    }

    @Bean
    ServletListenerRegistrationBean<ServletContextListener> servletListener() {
        ServletListenerRegistrationBean<ServletContextListener> srb
                = new ServletListenerRegistrationBean<>();
        srb.setListener(new MongoDbServerStartStopConfig());
        return srb;
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("Callback triggered - ContextListener.");
        try {
            final Process exec = Runtime.getRuntime().exec("lsof -i -n -P | grep \"mongod\"");
            final InputStream output = exec.getInputStream();
            final Stream<String> lines = new BufferedReader(new InputStreamReader(output)).lines();
            lines.collect(Collectors.toList());
        } catch (IOException e) {
            log.error("error stopping mongodb server. {}", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("Triggers when context initializes");
    }

}
