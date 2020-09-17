package com.gg.service;

import com.gg.beans.*;
import com.gg.vo.CourseAndDescVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

public interface CourseService {
    Course save(CourseAndDescVO course);

    Chapter saveChapter(Chapter chapter);

    List<Chapter> getAllChapterByCourseId(String courseId);

    Course getCourseById(String id);

    void deleteChapterById(String id);

    SubChapter addSubChapter(SubChapter subChapter);

    void deleteSubChapterById(String id);

    Object getCoursePublishVO(String id);


    void publishCourse(String id);

    void deleteCourseById(String id);

    Page<Course> findAllByConditions(String keyword, String min, String max, PageRequest pageRequest);

    Page<Course> getFrontCourseByCondition(String subLv1Id, String subLv2Id, PageRequest pageRequest);

    List<SubjectWithChildren> getAllSubject();

    Map getCourseDetailById(String id);

    List<Object> getCommentByCourseId(Integer id);

    CourseComment saveComment(CourseComment courseComment);
}
