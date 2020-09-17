package com.gg.mapper;

import com.gg.beans.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMapper extends JpaRepository<Project,Integer> {
}
