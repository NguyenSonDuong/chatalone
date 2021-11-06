package com.nguyenduong.chatalone;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication()
@Configuration
@ComponentScan(basePackages = "com.nguyenduong.chatalone")
public class ChatAloneApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatAloneApplication.class, args);
    }
}
