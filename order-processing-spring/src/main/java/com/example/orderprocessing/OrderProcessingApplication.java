package com.example.orderprocessing;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;

import com.example.orderprocessing.ingestion.EventReader;
import com.example.orderprocessing.observer.AlertObserver;
import com.example.orderprocessing.observer.LoggerObserver;
import com.example.orderprocessing.service.EventProcessor;
import com.example.orderprocessing.service.NotificationService;

import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OrderProcessingApplication {

    @Value("${ingest.on-start:true}")
    private boolean ingestOnStart;

    @Value("${ingest.file:events.ndjson}")
    private String ingestFile;

    public static void main(String[] args) {
        SpringApplication.run(OrderProcessingApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(EventReader reader, EventProcessor processor) {
        return args -> {
            if (ingestOnStart) {
                reader.readAndProcessFile(ingestFile, processor);
            }
        };
    }

    @Bean
    public LoggerObserver loggerObserver() {
        return new LoggerObserver();
    }

    @Bean
    public AlertObserver alertObserver() {
        return new AlertObserver();
    }

    @Bean
    public NotificationService notificationService(LoggerObserver loggerObserver, AlertObserver alertObserver) {
        NotificationService service = new NotificationService();
        service.register(loggerObserver);
        service.register(alertObserver);
        return service;
    }
}
