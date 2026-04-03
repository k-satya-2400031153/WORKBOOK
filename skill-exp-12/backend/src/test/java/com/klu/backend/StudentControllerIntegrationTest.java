package com.klu.backend;

import com.klu.backend.model.Student;
import com.klu.backend.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class StudentControllerIntegrationTest {

    @Autowired
    private StudentService studentService;

    @Test
    void shouldPerformCrudFlow() {
        Student created = studentService.createStudent(new Student(null, "Asha", "asha@example.com", "CSE"));
        assertTrue(created.getId() != null);

        List<Student> students = studentService.getAllStudents();
        assertEquals(1, students.size());
        assertEquals("Asha", students.get(0).getName());

        Optional<Student> updated = studentService.updateStudent(created.getId(),
                new Student(null, "Asha R", "asha.r@example.com", "ECE"));
        assertTrue(updated.isPresent());
        assertEquals("Asha R", updated.get().getName());
        assertEquals("ECE", updated.get().getCourse());

        boolean deleted = studentService.deleteStudent(created.getId());
        assertTrue(deleted);
        assertFalse(studentService.deleteStudent(created.getId()));
    }
}

