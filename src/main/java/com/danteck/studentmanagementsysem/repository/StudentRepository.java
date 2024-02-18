package com.danteck.studentmanagementsysem.repository;

import com.danteck.studentmanagementsysem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
