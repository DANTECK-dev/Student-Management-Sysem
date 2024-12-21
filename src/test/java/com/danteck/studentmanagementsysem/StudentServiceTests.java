package com.danteck.studentmanagementsysem;

import com.danteck.studentmanagementsysem.entity.Student;
import com.danteck.studentmanagementsysem.repository.StudentRepository;
import com.danteck.studentmanagementsysem.service.StudentService;
import com.danteck.studentmanagementsysem.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@SpringBootTest
public class StudentServiceTests {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student1;
    private Student student2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        student1 = new Student("John", "Doe", "john.doe@example.com");
        student1.setId(1L);
        student2 = new Student("Jane", "Smith", "jane.smith@example.com");
        student2.setId(2L);
    }

    @Test
    public void testGetAllStudents() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student1, student2));

        List<Student> students = studentService.getAllStudents();

        assertNotNull(students);
        assertEquals(2, students.size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    public void testSaveStudent() {
        when(studentRepository.save(student1)).thenReturn(student1);

        studentService.saveStudent(student1);

        verify(studentRepository, times(1)).save(student1);
    }

    @Test
    public void testGetStudentById() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student1));

        Student foundStudent = studentService.getStudentById(1L);

        assertNotNull(foundStudent);
        assertEquals("John", foundStudent.getFirstName());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateStudent() {
        when(studentRepository.save(student1)).thenReturn(student1);

        studentService.updateStudent(student1);

        verify(studentRepository, times(1)).save(student1);
    }

    @Test
    public void testDeleteStudentById() {
        doNothing().when(studentRepository).deleteById(1L);

        studentService.deleteStudentById(1L);

        verify(studentRepository, times(1)).deleteById(1L);
    }
}
