package com.danteck.studentmanagementsysem.controller;

import com.danteck.studentmanagementsysem.entity.Student;
import com.danteck.studentmanagementsysem.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //handler method to handle list students and return mode and view
    @GetMapping("/students")
    public String listStudent(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "students";
    }

    @GetMapping("/students/new")
    public String createStudentForm(Model model){

        // create student object to hold student form data
        Student student = new Student();
        model.addAttribute("students", student);
        return "create_student";
    }

    @PostMapping("/students")
    public String saveStudent(@ModelAttribute("students") Student student){
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/students/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model){
        Student student = studentService.getStudentById(id);
        if (student == null) {
            // Если студент с таким id не найден, перенаправляем на страницу со списком студентов
            return "redirect:/students?error=StudentNotFound";
        }
        model.addAttribute("students", student);
        return "edit_student";
    }

    @PostMapping("/students/{id}")
    public String updateStudent(@PathVariable Long id,
                                @ModelAttribute("students") Student student,
                                Model model){

        //get student from db by id
        Student existingStudent = studentService.getStudentById(id);
        if (existingStudent == null) {
            // Если студент с таким id не найден, перенаправляем на страницу со списком студентов
            return "redirect:/students?error=StudentNotFound";
        }

        // Обновляем данные существующего студента
        existingStudent.setId(id);
        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setEmail(student.getEmail());

        //save updated student object
        studentService.updateStudent(existingStudent);
        return "redirect:/students";
    }

    @GetMapping("/students/{id}")
    public String deleteStudent(@PathVariable Long id, Model model){
        Student existingStudent = studentService.getStudentById(id);
        if (existingStudent == null) {
            // Если студент с таким id не найден, перенаправляем на страницу со списком студентов
            return "redirect:/students?error=StudentNotFound";
        }

        studentService.deleteStudentById(id);
        return "redirect:/students";
    }

    @GetMapping("/")
    public String redirectToIndex(){
        return "redirect:/students";
    }
}
