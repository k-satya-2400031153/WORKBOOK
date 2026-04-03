package com.klu.backend.service;

import com.klu.backend.model.Student;
import com.klu.backend.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student createStudent(Student student) {
        student.setId(null);
        return studentRepository.save(student);
    }

    public Optional<Student> updateStudent(Long id, Student student) {
        return studentRepository.findById(id)
                .map(existing -> {
                    existing.setName(student.getName());
                    existing.setEmail(student.getEmail());
                    existing.setCourse(student.getCourse());
                    return studentRepository.save(existing);
                });
    }

    public boolean deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }
}

