package com.gg.mapper;

import com.gg.beans.Emp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpMapper extends JpaRepository<Emp,Integer> {
}
