package com.gg.mapper;

import com.gg.beans.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentMapper extends JpaRepository<Student,Integer> {
}
