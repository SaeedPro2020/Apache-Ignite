package com.example.apache.ignite;

import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.ignite.configuration.CacheConfiguration;

@Configuration
public class IgniteConfig {

    @Bean
    public Ignite igniteInstance() {
        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setPeerClassLoadingEnabled(true);

        // Configure cache with expiry policy
        CacheConfiguration<String, String> cacheCfg = new CacheConfiguration<>("jsonCache");
        cacheCfg.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));

        cfg.setCacheConfiguration(cacheCfg);


        return Ignition.start(cfg);
    }
}
