package com.korit.backend_mini;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class KoritGovProjectBackendMiniApplication {

    public static void main(String[] args) {
        SpringApplication.run(KoritGovProjectBackendMiniApplication.class, args);
    }

}
