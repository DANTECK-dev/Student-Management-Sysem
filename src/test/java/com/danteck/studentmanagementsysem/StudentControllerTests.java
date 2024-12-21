package com.danteck.studentmanagementsysem;

import com.danteck.studentmanagementsysem.controller.StudentController;
import com.danteck.studentmanagementsysem.entity.Student;
import com.danteck.studentmanagementsysem.exception.StudentNotFoundException;
import com.danteck.studentmanagementsysem.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.*;
        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
public class StudentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    private Student student1;
    private Student student2;

    @BeforeEach
    public void setUp() {
        student1 = new Student("John", "Doe", "john.doe@example.com");
        student1.setId(1L);
        student2 = new Student("Jane", "Smith", "jane.smith@example.com");
        student2.setId(2L);
    }

    @Test
    public void testListStudents() throws Exception {
        when(studentService.getAllStudents()).thenReturn(Arrays.asList(student1, student2));

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(view().name("students"))
                .andExpect(model().attributeExists("students"));

        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    public void testCreateStudentForm() throws Exception {
        mockMvc.perform(get("/students/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("create_student"))
                .andExpect(model().attributeExists("students"));
    }

    @Test
    public void testSaveStudent() throws Exception {
        mockMvc.perform(post("/students")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("email", "john.doe@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"));

        verify(studentService, times(1)).saveStudent(any(Student.class));
    }

    @Test
    public void testEditStudentForm() throws Exception {
        when(studentService.getStudentById(1L)).thenReturn(student1);

        mockMvc.perform(get("/students/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit_student"))
                .andExpect(model().attributeExists("students"));

        verify(studentService, times(1)).getStudentById(1L);
    }

    @Test
    public void testUpdateStudent() throws Exception {
        mockMvc.perform(post("/students/1")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("email", "john.doe@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"));

        verify(studentService, times(1)).updateStudent(any(Student.class));
    }

    @Test
    public void testDeleteStudentNotFound() throws Exception {
        // Имитируем ситуацию, когда студент не найден
        doThrow(new StudentNotFoundException("Student not found")).when(studentService).deleteStudentById(1L);

        mockMvc.perform(get("/students/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students?error=StudentNotFound"));

        verify(studentService, times(1)).deleteStudentById(1L);
    }

    @Test
    public void testDeleteStudentSuccessfully() throws Exception {
        // Имитируем успешное удаление студента
        doNothing().when(studentService).deleteStudentById(1L);

        mockMvc.perform(get("/students/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"));

        // Проверяем, что метод deleteStudentById был вызван один раз с аргументом 1L
        verify(studentService, times(1)).deleteStudentById(1L);
    }
}
