package com.github.Neelic.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.github.Neelic.demo.bot",
        "com.github.Neelic.demo.service",
        "com.github.Neelic.demo.repository",
        "com.github.Neelic.demo.javarushclient",
        "org.telegram.telegrambots"
})
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
