package com.gg.mapper;

import com.gg.beans.SubChapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubChapterMapper extends JpaRepository<SubChapter,String> {
    List<SubChapter> findByCourseIdOrderBySort(String courseId);

    @Query(value="select count(*) from sub_chapter where chapter_id=?1",nativeQuery = true)
    Integer findCountByChapterId(String id);
    @Modifying
    @Query(value="delete from sub_chapter where id=?1", nativeQuery = true)
    void CusDeleteChapterById(String id);

}
