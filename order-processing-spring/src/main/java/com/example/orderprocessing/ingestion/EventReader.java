//package com.example.orderprocessing.ingestion;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import com.example.orderprocessing.events.BaseEvent;
//import com.example.orderprocessing.service.EventProcessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@Component
//public class EventReader {
//    private static final Logger log = LoggerFactory.getLogger(EventReader.class);
//    private final ObjectMapper mapper;
//
//    public EventReader(ObjectMapper mapper) {
//        this.mapper = mapper;
//    }
//
//    public void readAndProcessFile(String filePath, EventProcessor processor) {
//        Path path = Path.of(filePath);
//        if (!Files.exists(path)) {
//            log.warn("Event file not found at {}. Skipping auto-ingest.", path.toAbsolutePath());
//            return;
//        }
//        try (BufferedReader br = Files.newBufferedReader(path)) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                if (line.isBlank()) continue;
//                try {
//                    BaseEvent event = mapper.readValue(line, BaseEvent.class);
//                    processor.process(event);
//                } catch (Exception ex) {
//                    log.warn("Failed to parse/process line: {} -> {}", line, ex.getMessage());
//                }
//            }
//        } catch (IOException e) {
//            log.error("Error reading event file {}: {}", filePath, e.getMessage());
//        }
//    }
//}
package com.example.orderprocessing.ingestion;

import com.example.orderprocessing.events.BaseEvent;
import com.example.orderprocessing.service.EventProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class EventReader {
    private static final Logger log = LoggerFactory.getLogger(EventReader.class);
    private final ObjectMapper mapper;
    private final ResourceLoader resourceLoader;

    public EventReader(ObjectMapper mapper, ResourceLoader resourceLoader) {
        this.mapper = mapper;
        this.resourceLoader = resourceLoader;
    }

    public void readAndProcessFile(String location, EventProcessor processor) {
        try {
            Resource resource = resourceLoader.getResource(location);
            if (!resource.exists()) {
                log.warn("Event file not found at {}. Skipping auto-ingest.", location);
                return;
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.isBlank()) continue;
                    try {
                        BaseEvent event = mapper.readValue(line, BaseEvent.class);
                        processor.process(event);
                    } catch (Exception ex) {
                        log.warn("Failed to parse/process line: {} -> {}", line, ex.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error reading event file {}: {}", location, e.getMessage());
        }
    }
}
