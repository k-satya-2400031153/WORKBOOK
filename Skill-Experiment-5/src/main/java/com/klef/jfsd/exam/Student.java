package com.klef.jfsd.exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Student {
    private int id;
    private String name;
    private String gender;
    @Autowired
    private Certification certification;

    // Default Constructor
    public Student() {
        this.id = 101;
        this.name = "K. Satya";
        this.gender = "Male";
    }

    @Override
    public String toString() {
        return "Student Details: \n" +
                "ID: " + id + "\n" +
                "Name: " + name + "\n" +
                "Gender: " + gender + "\n" +
                "Certification: " + certification.toString(); // This proves Autowiring worked
    }
}