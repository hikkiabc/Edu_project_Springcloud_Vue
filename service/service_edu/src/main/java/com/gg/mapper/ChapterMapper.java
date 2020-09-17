package com.gg.mapper;

import com.gg.beans.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChapterMapper extends JpaRepository<Chapter,String> {
    List<Chapter> findByCourseIdOrderBySort(String courseId);
    @Modifying
@Query(value="delete from chapter where id=?1", nativeQuery = true)
    void CusDeleteChapterById(String id);
}
