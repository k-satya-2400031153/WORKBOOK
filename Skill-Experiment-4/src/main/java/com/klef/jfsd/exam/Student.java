package com.klef.jfsd.exam;
public class Student {
    private int studentId;
    private String name;
    private String course;
    private int year;
    public Student() {
    }
    public Student(int studentId, String name, String course, int year) {
        this.studentId = studentId;
        this.name = name;
        this.course = course;
        this.year = year;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setYear(int year) {
        this.year = year;
    }
    @Override
    public String toString() {
        return "Student Details [ID=" + studentId + ", Name=" + name + ", Course=" + course + ", Year=" + year + "]";
    }
}