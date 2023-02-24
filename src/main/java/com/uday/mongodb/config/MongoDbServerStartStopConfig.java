package com.uday.mongodb.config;

import com.uday.mongodb.util.ScriptRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Arrays;

@Configuration
@Slf4j
public class MongoDbServerStartStopConfig {

    private ScriptRunner scriptRunner;
    private static boolean isWindows;

    @Autowired
    public MongoDbServerStartStopConfig(ScriptRunner scriptRunner) {
        this.scriptRunner = scriptRunner;
    }

    static {
        isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
    }

    @PostConstruct
    public void startMongoDbServer() throws IOException {
        log.info("starting mongodb server.");
        try {
            if (!isWindows) {
                scriptRunner.runScriptWithCommand(Arrays.asList("sh", this.getClass().getClassLoader().getResource("scripts/mongodb-server-start.sh").getPath()));
            } else {
                log.error("support not added for windows family. Please start mongodb server manually.");
            }
            log.info("mongodb server started.");
        } catch (RuntimeException e) {
            log.error("error starting mongodb. {}", e);
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    private void stopMongoDbServer() throws IOException {
        log.info("shutting down mongodb servers.");
        try {
            if (!isWindows) {
                scriptRunner.runScriptWithCommand(Arrays.asList("sh", this.getClass().getClassLoader().getResource("scripts/mongodb-server-stop.sh").getPath()));
            } else {
                log.error("support not added for windows family. Please stop mongodb server manually.");
            }
            log.info("mongodb servers shutdown.");
        } catch (RuntimeException e) {
            log.error("error shutting down mongodb. {}", e);
            throw new RuntimeException(e);
        }
    }
}
