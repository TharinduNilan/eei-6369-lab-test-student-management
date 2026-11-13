
package com.labtest.student_management.repository;

import com.labtest.student_management.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository
        extends JpaRepository<Student, Long> {

    boolean existsByEmail(String email);
}