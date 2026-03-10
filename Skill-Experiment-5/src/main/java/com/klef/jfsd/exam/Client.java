package com.klef.jfsd.exam;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Client {
    public static void main(String[] args) {
        // Load the Context
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Get the Student Bean
        Student student = context.getBean(Student.class);

        // Print Output
        System.out.println("--- Spring Autowiring Output ---");
        System.out.println(student.toString());
    }
}