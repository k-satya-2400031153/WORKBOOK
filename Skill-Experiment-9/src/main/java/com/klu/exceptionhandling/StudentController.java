package com.klu.exceptionhandling;

import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    // Dummy database testing ke liye
    private static final Map<Integer, Student> studentDB = new HashMap<>();
    static {
        studentDB.put(1, new Student(1, "Aarav", "Computer Science"));
        studentDB.put(2, new Student(2, "Meera", "Mechanical Engineering"));
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable String id) {
        int studentId;

        // Task 8: Check for invalid input format (like sending text instead of number)
        try {
            studentId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid format: Student ID must be a number. You entered '" + id + "'");
        }

        // Task 2: Throw StudentNotFoundException if ID doesn't exist
        if (!studentDB.containsKey(studentId)) {
            throw new StudentNotFoundException("Student not found with ID: " + studentId);
        }

        return studentDB.get(studentId);
    }
}