package com.klef.jfsd.exam;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
public class ClientAnnotation {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println("--- Annotation Configuration Output ---");
        Student s = (Student) context.getBean("studentBean");
        System.out.println(s.toString());
    }
}