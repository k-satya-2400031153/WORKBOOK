package com.klef.jfsd.exam;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean(name = "studentBean")
    public Student student() {
        return new Student(202, "Sita Ram", "Information Technology", 4);
    }
}