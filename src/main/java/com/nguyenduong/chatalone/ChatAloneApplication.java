package com.nguyenduong.chatalone;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.FileInputStream;
import java.io.InputStream;

@SpringBootApplication(scanBasePackages = "com.nguyenduong.chatalone")
@ComponentScan(basePackages = "com.nguyenduong.chatalone")
@EnableAutoConfiguration
@EnableJpaAuditing()
public class ChatAloneApplication {

    public static void main(String[] args) {

//ClassLoader classloader = Thread.currentThread().getContextClassLoader();
//        InputStream is = classloader.getResourceAsStream("serviceAccountKey.json");
//        try{
//            FirebaseOptions options = new FirebaseOptions.Builder()
//                    .setCredentials(GoogleCredentials.fromStream(is))
//                    .setDatabaseUrl("https://chatalone-10754-default-rtdb.asia-southeast1.firebasedatabase.app")
//                    .build();
//
//            FirebaseApp.initializeApp(options);
//        }catch (Exception ex){
//
//        }

        SpringApplication.run(ChatAloneApplication.class, args);
    }
}
