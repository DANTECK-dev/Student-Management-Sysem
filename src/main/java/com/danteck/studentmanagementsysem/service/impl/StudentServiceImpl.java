package com.danteck.studentmanagementsysem.service.impl;

import com.danteck.studentmanagementsysem.entity.Student;
import com.danteck.studentmanagementsysem.repository.StudentRepository;
import com.danteck.studentmanagementsysem.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        super();
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public void updateStudent(Student student) {
        studentRepository.save(student);
    }

    @Override
    public void deleteStudentById(Long id) {
        if (getStudentById(id) == null)
        {

        } else {
            studentRepository.deleteById(id);
        }
    }
}
