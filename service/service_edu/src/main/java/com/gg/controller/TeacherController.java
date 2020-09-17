package com.gg.controller;

import com.gg.beans.Course;
import com.gg.beans.Teacher;
import com.gg.utils.CommonResult;
import com.gg.mapper.TeacherMapper;
import com.gg.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/teacher")
@RestController
@Transactional
//@CrossOrigin
public class TeacherController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    TeacherMapper teacherMapper;

    @GetMapping("/{id}")
    public CommonResult findById(@PathVariable Integer id){
        Teacher teacher =teacherService.findById(id);
        CommonResult commonResult = new CommonResult();
        commonResult.setData(teacher);
        return commonResult;
    }
    @DeleteMapping("/{id}")
    public CommonResult deleteById(@PathVariable Integer id) {
        CommonResult commonResult = new CommonResult();
        try {
//            teacherService.deleteById(id);
            Integer res = teacherService.deleteByIdCus(id);
            commonResult.setData(res);
            commonResult.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            commonResult.setSuccess(false);
        }
        return commonResult;
    }


    @PostMapping("/page")
    public CommonResult page(@RequestBody(required = false) Map map) throws ParseException {
        if (map==null){
          return   new CommonResult(true,teacherMapper.findAll());
        }
        System.out.println(map);
        PageRequest pageRequest = PageRequest.of((Integer) map.get("pageNum") - 1,
                (Integer) map.get("pageSize"), Sort.by("start_time").descending().and(Sort.by("id").ascending()));
//        Date min = new SimpleDateFormat("dd-MM-yyyy").parse("07-29-2020");
//        Date max = new SimpleDateFormat("dd-MM-yyyy").parse("07-30-2020");
////        Page<Teacher> teacherPage = teacherMapper.findAll(pageRequest);

        Page<Teacher> teacherList = teacherMapper.findAllBySpecifications((String) map.get("keyword"), (String) map.get("min"), (String) map.get("max"), pageRequest);
//
////        return new CommonResult(true,teacherPage.getContent());
        return new CommonResult(true, teacherList);

    }

    @PostMapping("/")
    public CommonResult addOrUpdate(@RequestBody(required = false) Teacher teacher) {
        System.out.println(teacher);
        teacher.setUpdateTime(new Date());
        CommonResult commonResult = new CommonResult();
        try {
            Teacher res = teacherService.save(teacher);
            commonResult.setData(res);
        } catch (Exception e) {
            e.printStackTrace();
            commonResult.setData("fail");
        }
        return commonResult;
    }

    @PostMapping("/a/update")
    public CommonResult update(@RequestBody(required = false) Teacher teacher) {
        Integer update = teacherMapper.update(teacher);

        return new CommonResult(true, update);
    }

    @PostMapping("/login")
    public CommonResult login() {
        CommonResult commonResult = new CommonResult();
        Map<String, Object> map = CommonResult.createMap();
        map.put("token", "admin");
        commonResult.setData(map);
        return commonResult;
    }

    @GetMapping("/info")
    public CommonResult getInfo() {
        CommonResult commonResult = new CommonResult();
        Map<String, Object> map = CommonResult.createMap();
        map.put("roles", "ad7min");
        map.put("name", "name");
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("introduction", "introduction");
        commonResult.setData(map);
        return commonResult;
    }

    @GetMapping("/hot")
    public CommonResult getTeacherAndCourse(){
      Map<String,Object> res=  teacherService.getTeacherAndCourse();
        CommonResult commonResult = new CommonResult();
        commonResult.setData(res);
        return commonResult;
    }
    @PostMapping("/getfrontteacher/{num}/{size}")
    public CommonResult getFrontTeacher(@PathVariable Integer num, @PathVariable Integer size){
        PageRequest pageRequest = PageRequest.of(num - 1, size,Sort.by("id").ascending());
        Page<Teacher> teacherPage = teacherService.getFrontTeacher(pageRequest);
        CommonResult commonResult = new CommonResult();
        commonResult.setData(teacherPage);
        return commonResult;
    }
    @GetMapping("/frontteacher/{id}")
    public  CommonResult getFrontTeacherById(@PathVariable Integer id){
     Teacher teacher=   teacherService.getTeacherById(id);
        List<Course> courseList=teacherService.getCourseByTeacherId(id);
        CommonResult commonResult = new CommonResult();
        Map<String, Object> map = new HashMap<>();
        map.put("course",courseList);
        map.put("teacher",teacher);
        commonResult.setData(map);
        return  commonResult;
    }
}
