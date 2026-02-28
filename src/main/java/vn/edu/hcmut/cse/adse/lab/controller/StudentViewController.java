package vn.edu.hcmut.cse.adse.lab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmut.cse.adse.lab.entity.Student;
import vn.edu.hcmut.cse.adse.lab.service.StudentService;

import java.util.List;

@Controller
public class StudentViewController {

    @Autowired
    private StudentService studentService;

    @GetMapping({"/", "/students"})
    public String listStudents(@RequestParam(required = false) String keyword, Model model) {
        List<Student> students;

        if (keyword != null && !keyword.trim().isEmpty()) {
            students = studentService.searchByName(keyword.trim());
        } else {
            students = studentService.getAll();
        }

        model.addAttribute("students", students);
        model.addAttribute("keyword", keyword);
        return "index";
    }

    @GetMapping("/students/{id}")
    public String showStudentDetail(@PathVariable String id, Model model) {
        Student student = studentService.getById(id);
        if(student == null) {
            return "redirect:/students";
        }
        model.addAttribute("student", student);
        return "detail";
    }

    @GetMapping("/students/add")
    public  String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        return "add-student";
    }

    @PostMapping("/students/save")
    public String saveStudent(@ModelAttribute("student") Student student) {
        studentService.save(student);
        return "redirect:/students/" + student.getId();
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable String id) {
        studentService.delete(id);
        return "redirect:/students";
    }

    @GetMapping("/students/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Student student = studentService.getById(id);
        model.addAttribute("student", student);
        return "edit-student";
    }
}