package com.klu.backend.controller;
import com.klu.backend.model.Student;
import com.klu.backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService service;

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) { return ResponseEntity.ok(service.addStudent(student)); }
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() { return ResponseEntity.ok(service.getAllStudents()); }
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) { return ResponseEntity.ok(service.updateStudent(id, student)); }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) { service.deleteStudent(id); return ResponseEntity.ok("Deleted"); }
}