package com.klef.jfsd.exam;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class ClientXML {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println("--- Constructor Injection Output ---");
        Student s1 = (Student) context.getBean("studentConstructor");
        System.out.println(s1.toString());
        System.out.println("--- Setter Injection Output ---");
        Student s2 = (Student) context.getBean("studentSetter");
        System.out.println(s2.toString());
    }
}