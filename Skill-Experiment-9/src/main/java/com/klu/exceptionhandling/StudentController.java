package com.klu.exceptionhandling;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {

    // Temporary list to store students without needing a database
    private final List<Student> studentList = new ArrayList<>();

    public StudentController() {
        // Pre-loading one student so ID 101 always works out of the box
        studentList.add(new Student(101, "James", "Computer Science"));
    }

    // New POST endpoint to add a student dynamically
    @PostMapping("/add")
    public String addStudent(@RequestBody Student student) {
        studentList.add(student);
        return "Student " + student.getName() + " added successfully with ID: " + student.getId();
    }

    // The GET endpoint with our Exception Handling
    @GetMapping("/{id}")
    public Student getStudent(@PathVariable String id) {
        int parsedId;

        // Trigger InvalidInputException if they pass text instead of a number
        try {
            parsedId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid format. ID must be a number, not text.");
        }

        // Search the list for the matching ID
        Optional<Student> targetStudent = studentList.stream()
                .filter(s -> s.getId() == parsedId)
                .findFirst();

        // Trigger StudentNotFoundException if the ID isn't in our list
        if (targetStudent.isPresent()) {
            return targetStudent.get();
        } else {
            throw new StudentNotFoundException("Student with ID " + parsedId + " does not exist.");
        }
    }
}