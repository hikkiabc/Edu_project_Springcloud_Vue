package com.gg.mapper;

import com.gg.beans.CourseComment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseCommentMapper extends JpaRepository<CourseComment,Integer> {
    List<CourseComment> findByCourseId(Integer id, Sort sort);
}
