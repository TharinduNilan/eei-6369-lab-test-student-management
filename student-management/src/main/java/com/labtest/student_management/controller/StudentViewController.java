
package com.labtest.student_management.controller;

import com.labtest.student_management.entity.Student;
import com.labtest.student_management.service.StudentService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentViewController {

    private final StudentService studentService;

    public StudentViewController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public String students(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "students";
    }

    @GetMapping("/students/new")
    public String newStudent(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("pageTitle", "Add New Student");
        return "student-form";
    }

    @PostMapping("/students")
    public String createStudent(
            @Valid @ModelAttribute("student") Student student,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Add New Student");
            return "student-form";
        }

        try {
            studentService.createStudent(student);
            return "redirect:/";
        } catch (IllegalArgumentException ex) {
            result.rejectValue("email", "duplicate", ex.getMessage());
            model.addAttribute("pageTitle", "Add New Student");
            return "student-form";
        }
    }

    @GetMapping("/students/{id}/edit")
    public String editStudent(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.getStudentById(id));
        model.addAttribute("pageTitle", "Edit Student");
        return "student-form";
    }

    @PostMapping("/students/{id}/update")
    public String updateStudent(
            @PathVariable Long id,
            @Valid @ModelAttribute("student") Student student,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Edit Student");
            return "student-form";
        }

        studentService.updateStudent(id, student);
        return "redirect:/";
    }

    @PostMapping("/students/{id}/delete")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/";
    }
}