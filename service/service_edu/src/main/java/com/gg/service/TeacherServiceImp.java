package com.gg.service;

import com.gg.beans.Course;
import com.gg.beans.Teacher;
import com.gg.mapper.CourseMapper;
import com.gg.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class TeacherServiceImp implements TeacherService {
    @Autowired
    TeacherMapper teacherMapper;
@Autowired
    CourseMapper courseMapper;
    @Override

    public void deleteById(Integer id) {
        teacherMapper.deleteById(id);
    }

    public Integer deleteByIdCus(Integer id){
       return teacherMapper.deleteByIdCus(id);
    }

    @Override
    public Teacher findById(Integer id) {
//        System.out.println(teacherMapper.getOne(id));
        Teacher teacher = teacherMapper.getOne(id);

        return teacher;
    }

    @Override
    public Map<String, Object> getTeacherAndCourse() {
        Page<Teacher> teacherPage = teacherMapper.findAll(PageRequest.of(0, 4));
        Page<Course> coursePage = courseMapper.findAll(PageRequest.of(0, 4));
        Map<String, Object> map = new HashMap<>();
        map.put("teacher",teacherPage);
        map.put("course",coursePage);
        return map;
    }

    @Override
    public Page<Teacher> getFrontTeacher(PageRequest pageRequest) {
        Page<Teacher> teacherPage = teacherMapper.findAll(pageRequest);
        return teacherPage;
    }

    @Override
    public Teacher getTeacherById(Integer id) {
        return teacherMapper.findById(id).get();
    }

    @Override
    public List<Course> getCourseByTeacherId(Integer id) {
        return courseMapper.getByTeacherId(String.valueOf(id) );
    }

    @Override
    public Teacher save(Teacher teacher) {
        Teacher save = teacherMapper.save(teacher);

        return save;
    }
}
