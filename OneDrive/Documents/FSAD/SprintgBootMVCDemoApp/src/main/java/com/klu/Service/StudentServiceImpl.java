package com.klu.service;

import com.klu.model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StudentServiceImpl implements StudentService {

    private final List<Student> students = new CopyOnWriteArrayList<>();
    private final AtomicInteger idSequence = new AtomicInteger(1);

    public StudentServiceImpl() {
        // seed with a sample student so list is non-empty on first run
        students.add(new Student(idSequence.getAndIncrement(), "John Doe", "john.doe@example.com", "Computer Science"));
    }

    @Override
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    @Override
    public Student getStudentById(int id) {
        return students.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Student createStudent(Student student) {
        if (student.getId() == 0) {
            student.setId(idSequence.getAndIncrement());
        }
        students.add(student);
        return student;
    }

    @Override
    public Student updateStudent(int id, Student student) {
        Student existing = getStudentById(id);
        if (existing == null) {
            return null;
        }
        existing.setName(student.getName());
        existing.setEmail(student.getEmail());
        existing.setCourse(student.getCourse());
        return existing;
    }

    @Override
    public boolean deleteStudent(int id) {
        return students.removeIf(student -> student.getId() == id);
    }
}

