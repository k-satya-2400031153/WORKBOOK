package com.klef.jfsd.exam;

import org.springframework.stereotype.Component;

@Component
public class Certification {
    private int id;
    private String name;
    private String dateOfCompletion;
    public Certification() {
        this.id = 9001;
        this.name = "Oracle Certified Java Associate";
        this.dateOfCompletion = "15-Dec-2025";
    }

    @Override
    public String toString() {
        return "[ID=" + id + ", Name=" + name + ", Date=" + dateOfCompletion + "]";
    }
}