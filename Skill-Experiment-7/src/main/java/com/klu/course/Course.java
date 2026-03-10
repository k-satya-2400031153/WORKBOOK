package com.klu.course;

public class Course {
    private String courseId;
    private String title;
    private int duration;
    private double fee;

    public Course() {}

    public Course(String courseId, String title, int duration, double fee) {
        this.courseId = courseId;
        this.title = title;
        this.duration = duration;
        this.fee = fee;
    }

    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
    public double getFee() { return fee; }
    public void setFee(double fee) { this.fee = fee; }
}