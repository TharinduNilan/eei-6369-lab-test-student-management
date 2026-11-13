
package com.labtest.student_management.service;

import com.labtest.student_management.entity.Student;
import com.labtest.student_management.repository.StudentRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Create Student
    public Student createStudent(Student student) {

        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new IllegalArgumentException(
                "Email already exists"
            );
        }

        return studentRepository.save(student);
    }

    // Get All Students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Get Student By ID
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Student not found")
                );
    }

    // Update Student
    public Student updateStudent(Long id, Student updatedStudent) {

        Student student = getStudentById(id);

        student.setFullName(updatedStudent.getFullName());
        student.setEmail(updatedStudent.getEmail());
        student.setPhone(updatedStudent.getPhone());
        student.setCourse(updatedStudent.getCourse());

        return studentRepository.save(student);
    }

    // Delete Student
    public void deleteStudent(Long id) {

        Student student = getStudentById(id);

        studentRepository.delete(student);
    }
}