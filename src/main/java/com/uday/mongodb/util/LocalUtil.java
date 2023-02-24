package com.uday.mongodb.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class LocalUtil {
    public static void main(String[] args) {
        try {
            final Process execStop = Runtime.getRuntime().exec("lsof -i -n -P");
            final InputStream output1 = execStop.getInputStream();
            final Stream<String> lines1 = new BufferedReader(new InputStreamReader(output1)).lines();
            new Thread(() -> {
                //lines1.filter(l -> l.contains("java")).map(m -> {return m.split("\\s+");}).forEach(v -> log.info(v[0] + " " + v[1]));
                final List<String> mongodPorts = lines1.filter(l -> l.contains("mongo")).map(p -> p.split("\\s")[1]).collect(Collectors.toList());
                mongodPorts.stream().forEach(p -> {
                    try {
                        Runtime.getRuntime().exec("kill -9 "+ p);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                final Stream<String> err = new BufferedReader(new InputStreamReader(execStop.getErrorStream())).lines();
                err.map(e -> "err>>" + e).forEach(log::error);
            }, "mongodb-stopper-thread").start();
        } catch (IOException e) {
            log.error("error stopping mongodb server. {}", e);
            throw new RuntimeException(e);
        }
    }
}
