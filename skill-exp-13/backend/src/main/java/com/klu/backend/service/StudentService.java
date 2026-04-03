package com.klu.backend.service;
import com.klu.backend.model.Student;
import com.klu.backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repository;
    public Student addStudent(Student student) { return repository.save(student); }
    public List<Student> getAllStudents() { return repository.findAll(); }
    public Student updateStudent(Long id, Student updatedData) {
        Student existing = repository.findById(id).orElseThrow();
        existing.setName(updatedData.getName());
        existing.setEmail(updatedData.getEmail());
        existing.setCourse(updatedData.getCourse());
        return repository.save(existing);
    }
    public void deleteStudent(Long id) { repository.deleteById(id); }
}