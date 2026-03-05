package com.klu.service;

import com.klu.model.Student;
import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();
    Student getStudentById(int id);
    Student createStudent(Student student);
    Student updateStudent(int id, Student student);
    boolean deleteStudent(int id);
}

