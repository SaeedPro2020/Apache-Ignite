package com.example.apache.ignite;

import org.apache.ignite.Ignite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IgniteInitializer {
    private static Ignite ignite;

    @Autowired
    public void setIgnite(Ignite ignite) {
        IgniteInitializer.ignite = ignite;
    }

    public static Ignite getIgnite() {
        return ignite;
    }
}
