package com.example.apache.ignite;

import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.apache.utility.getCurrentHour;

@Component
public class IgniteCacheReader {

    @Autowired
    private getCurrentHour currentHourKey;

    public String readFromCache() {
        try {
            IgniteCache<String, String> cache = Ignition.ignite().cache("jsonCache");

            String key = currentHourKey.getCurrentHourKey();
            // Retrieve data from cache
            String data = cache.get(key);

            // Print the retrieved data
            System.out.println("Data from cache: " + data);
            return data.toString();
        } catch (IgniteException e) {
            e.printStackTrace();
            return e.toString();
        }
    }
}
