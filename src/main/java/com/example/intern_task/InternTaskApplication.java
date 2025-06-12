package com.example.intern_task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class InternTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(InternTaskApplication.class, args);
    }

}
