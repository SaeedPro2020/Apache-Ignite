package com.example.apache;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.lang.IgniteRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apache.ignite.IgniteCacheReader;
import com.example.apache.utility.OneHourTimer;
import com.example.utility.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class MyController {

    @Autowired
    private IgniteCacheReader cacheReader;

    @Autowired
    private OneHourTimer oneHourTimer;

    private String getCurrentHourKey() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH"));
    }

    @GetMapping("/generate-data")
    public String generateData() {
        try {
            Ignite ignite = Ignition.ignite();
            ExecutorService exec = ignite.executorService();

            // Create a task to generate data and store it in the cache
            IgniteRunnable task = new IgniteRunnable() {
                @Override
                public void run() {
                    try {
                        IgniteCache<String, String> cache = ignite.getOrCreateCache("jsonCache");

                        // Generate data using the utility method
                        Container container = DataGeneratorUtils.generateData();

                        // Convert Container object to JSON string
                        ObjectMapper objectMapper = new ObjectMapper();
                        String jsonString = objectMapper.writeValueAsString(container);

                        // Get the current hour key
                        String currentHourKey = getCurrentHourKey();

                        // Conditionally update the cache
                        if (oneHourTimer.isFlag()) {
                            cache.put(currentHourKey, jsonString);
                            oneHourTimer.resetFlag(); // Reset the flag and start the timer
                        } else {
                            System.out.println("Data in cache won't change");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            // Submit the task to the executor service
            Future<?> future = exec.submit(task);

            // Wait for the task to complete and return the result
            future.get();

            return "Data generation task submitted.";
        } catch (Exception e) {
            return "Error generating data: " + e.getMessage();
        }
    }

    @GetMapping("/read-cache")
    public Map<String, Object> readCache() {
        Map<String, Object> response = new HashMap<>();
        try {
            String valueFromCache = cacheReader.readFromCache();
            response.put("status", valueFromCache);
        } catch (Exception e) {
            response.put("error", e.getMessage());
        }
        return response;
    }
}
