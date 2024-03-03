package com.martynov.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class MyCartApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyCartApplication.class, args);
    }
}
