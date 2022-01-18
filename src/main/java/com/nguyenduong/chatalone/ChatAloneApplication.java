package com.nguyenduong.chatalone;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.nguyenduong.chatalone")
@ComponentScan(basePackages = "com.nguyenduong.chatalone")
@EnableAutoConfiguration
@EnableJpaAuditing()
public class ChatAloneApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatAloneApplication.class, args);
    }
}
