package com.uday.mongodb.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Component
@Slf4j
public class ScriptRunner {

    public void runScriptWithCommand(List<String> commands) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(commands);
        log.debug("commands: {}", pb.command());
        BufferedReader reader = null;
        BufferedReader errorReader = null;
        pb.redirectErrorStream(true);
        pb.inheritIO();
        try {
            Process p = pb.start();
            reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                log.info(">> {}", line);
            }

            int exitVal = p.waitFor();
            if (exitVal == 0) {
                log.info("command executed successfully!");
            } else {
                //abnormal...
                String error;
                while ((error = errorReader.readLine()) != null) {
                    log.error("<ERROR> {}", error);
                }
                log.error("#### exit value: {}", exitVal);
                throw new RuntimeException(String.format("error executing command with exit value %d", exitVal));
            }
        } catch (IOException e) {
            log.error("error reading command logs. {}", e);
            throw new RuntimeException("error executing command.", e);
        } catch (InterruptedException e) {
            log.error("process interrupted. {}", e);
            throw new RuntimeException("error executing command.", e);
        } finally {
            if (null != reader) {
                reader.close();
            }
            if (null != errorReader) {
                errorReader.close();
            }
        }
    }
}
