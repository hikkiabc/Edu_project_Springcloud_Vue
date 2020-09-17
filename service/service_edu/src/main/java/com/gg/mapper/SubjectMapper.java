package com.gg.mapper;

import com.gg.beans.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectMapper extends JpaRepository<Subject,Integer>, JpaSpecificationExecutor<Subject> {
        Subject findByTitleAndPid(String title,Integer pid);
        Subject findByTitle(String title);
        List<Subject> findByPid(Integer pid);
        @Query(value="select * from subject where pid != 0",nativeQuery = true)
        List<Subject> findAll2Subjects();

}
