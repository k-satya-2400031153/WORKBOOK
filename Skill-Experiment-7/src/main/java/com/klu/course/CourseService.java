package com.klu.course;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private List<Course> courses = new ArrayList<>();

    public Course addCourse(Course course) {
        courses.add(course);
        return course;
    }

    public List<Course> getAllCourses() {
        return courses;
    }

    public Optional<Course> getCourseById(String courseId) {
        return courses.stream().filter(c -> c.getCourseId().equals(courseId)).findFirst();
    }

    public Course updateCourse(String courseId, Course updatedCourse) {
        for (Course course : courses) {
            if (course.getCourseId().equals(courseId)) {
                course.setTitle(updatedCourse.getTitle());
                course.setDuration(updatedCourse.getDuration());
                course.setFee(updatedCourse.getFee());
                return course;
            }
        }
        return null;
    }

    public boolean deleteCourse(String courseId) {
        return courses.removeIf(c -> c.getCourseId().equals(courseId));
    }

    public List<Course> searchCoursesByTitle(String title) {
        return courses.stream()
                .filter(c -> c.getTitle().toLowerCase().contains(title.toLowerCase()))
                .toList();
    }
}