package com.gg.service;

import com.gg.beans.Course;
import com.gg.beans.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

public interface TeacherService {
    void deleteById(Integer id);

    Teacher save(Teacher teacher);
    Integer deleteByIdCus(Integer id);

    Teacher findById(Integer id);

    Map<String, Object> getTeacherAndCourse();

    Page<Teacher> getFrontTeacher(PageRequest pageRequest);

    Teacher getTeacherById(Integer id);

    List<Course> getCourseByTeacherId(Integer id);
}
