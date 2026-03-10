package com.klu.course;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<String> addCourse(@RequestBody Course course) {
        if (course.getCourseId() == null || course.getTitle() == null) {
            return new ResponseEntity<>("Error: Course ID and Title are required", HttpStatus.BAD_REQUEST);
        }
        courseService.addCourse(course);
        return new ResponseEntity<>("Success: Course added successfully", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable String id) {
        Optional<Course> course = courseService.getCourseById(id);
        if (course.isPresent()) {
            return new ResponseEntity<>(course.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error: Course not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCourse(@PathVariable String id, @RequestBody Course updatedCourse) {
        Course course = courseService.updateCourse(id, updatedCourse);
        if (course != null) {
            return new ResponseEntity<>("Success: Course updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error: Course not found for update", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable String id) {
        boolean isRemoved = courseService.deleteCourse(id);
        if (isRemoved) {
            return new ResponseEntity<>("Success: Course deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error: Course not found for deletion", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<?> searchCourses(@PathVariable String title) {
        List<Course> foundCourses = courseService.searchCoursesByTitle(title);
        if (!foundCourses.isEmpty()) {
            return new ResponseEntity<>(foundCourses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error: No courses found with title containing '" + title + "'", HttpStatus.NOT_FOUND);
        }
    }
}