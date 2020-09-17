package com.gg.controller;

import com.gg.beans.*;
import com.gg.feign.OosFeign;
import com.gg.service.CourseService;
import com.gg.utils.CommonResult;
import com.gg.vo.CourseAndDescVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/teacher/course")
@CrossOrigin
@Transactional
public class CourseController {
    @Autowired
    CourseService courseService;
    @Autowired
    OosFeign oosFeign;

    @PostMapping("/addcourse")
    public CommonResult addCourse(@RequestBody CourseAndDescVO courseAndDescVO) {
        CommonResult commonResult = new CommonResult();

        try {
            Course course = courseService.save(courseAndDescVO);
            commonResult.setData(course);
        } catch (Exception e) {
            e.printStackTrace();
            commonResult.setSuccess(false);
        }
        return commonResult;
    }

    @GetMapping("/chapter/{courseId}")
    public CommonResult getAllChapterByCourseId(@PathVariable String courseId) {

        List<Chapter> chapterList = courseService.getAllChapterByCourseId(courseId);
        CommonResult commonResult = new CommonResult();
        commonResult.setData(chapterList);
        return commonResult;
    }

    @GetMapping("/{id}")
    public CommonResult getCourseById(@PathVariable String id) {
        Course course = courseService.getCourseById(id);
        CommonResult commonResult = new CommonResult();
        commonResult.setData(course);
        return commonResult;
    }

    @PostMapping("/chapter")
    public CommonResult saveChapter(@RequestBody Chapter chapter) {

        Chapter chapter1 = courseService.saveChapter(chapter);
        CommonResult commonResult = new CommonResult();
        commonResult.setData(chapter1);
        return commonResult;
    }

    @PostMapping("/chapter/{id}")
    public CommonResult deleteChapterById(@PathVariable String id) {
        CommonResult commonResult = new CommonResult();
        try {
            courseService.deleteChapterById(id);
        } catch (Exception e) {
            e.printStackTrace();
            commonResult.setSuccess(false);
        }
        return commonResult;
    }

    @PostMapping("/subchapter")
    public CommonResult addSubChapter(@RequestBody SubChapter subChapter) {
        CommonResult commonResult = new CommonResult();
        SubChapter subChapter1 = courseService.addSubChapter(subChapter);
        commonResult.setData(subChapter1);
        return commonResult;
    }

    @DeleteMapping("/subchapter/{id}")
    public CommonResult deleteSubChapterById(@PathVariable String id) {
        courseService.deleteSubChapterById(id);
        CommonResult commonResult = new CommonResult();
        return commonResult;
    }

    @GetMapping("/coursepublish/{id}")
    public CommonResult getCoursePublishByCourseId(@PathVariable String id) {
        Object coursePublishVO = courseService.getCoursePublishVO(id);
        CommonResult commonResult = new CommonResult();
        commonResult.setData(coursePublishVO);
        return commonResult;
    }

    @PostMapping("/")
    public CommonResult getAllCourse(@RequestBody Map map) {

        Map query = (Map) map.get("query");
        CommonResult commonResult = new CommonResult();
        System.out.println(query);
        PageRequest pageRequest = PageRequest.of((Integer) map.get("pageNum") - 1, (Integer) map.get("pageSize"), Sort.by("id").ascending());

        Page<Course> courseList = courseService.findAllByConditions((String) query.get("keyword"), (String) query.get("min"), (String) query.get("max"), pageRequest);
        commonResult.setData(courseList);
        return commonResult;
    }

    @PostMapping("/coursepublish/{id}")
    public CommonResult publishCourse(@PathVariable String id) {

        CommonResult commonResult = new CommonResult();
        try {
            courseService.publishCourse(id);

        } catch (Exception e) {
            e.printStackTrace();
            commonResult.setSuccess(false);
        }
        return commonResult;
    }

    @DeleteMapping("/{id}")
    public CommonResult deleteCourseById(@PathVariable String id) {
        CommonResult commonResult = new CommonResult();
        try {
            courseService.deleteCourseById(id);
        } catch (Exception e) {
            e.printStackTrace();
            commonResult.setSuccess(false);
        }
        return commonResult;
    }

    @GetMapping("/test/{id}")
    public Object test(@PathVariable String id) {
        ArrayList<Object> list = new ArrayList<>();
        Object res = oosFeign.test(id);
        List<Integer> list1 = Arrays.asList(1, 2, 3);
//        StringUtils.join(list1.toArray(),",");
        list.add(list1);
        list.add(res);
        return list;
    }

    @PostMapping("/frontCourse")
    public CommonResult getFrontCourseByCondition(@RequestBody Map map) {
        String subLv1Id = (String) map.get("subLv1Id");
        System.out.println(map);
        String subLv2Id = (String) map.get("subLv2Id");
        Integer order = (Integer) map.get("order");
        PageRequest pageRequest;
        if (order == 1) {
            pageRequest = PageRequest.of((Integer) map.get("pageNum") - 1, (Integer) map.get("pageSize"),
                    Sort.by((String) map.get("sortBy")).descending());
        } else {
            pageRequest = PageRequest.of((Integer) map.get("pageNum") - 1, (Integer) map.get("pageSize"),
                    Sort.by((String) map.get("sortBy")).ascending());
        }
        Page<Course> coursePage = courseService.getFrontCourseByCondition(subLv1Id, subLv2Id, pageRequest);

        CommonResult commonResult = new CommonResult();
        commonResult.setData(coursePage);
        return commonResult;
    }

    @GetMapping("/subject")
    public CommonResult getAllSubject() {
        List<SubjectWithChildren> list = courseService.getAllSubject();
        CommonResult commonResult = new CommonResult();
        commonResult.setData(list);
        return commonResult;
    }

    @GetMapping("/frontcourse/{id}")
    public CommonResult getCourseDetailById(@PathVariable String id) {
        Map map = courseService.getCourseDetailById(id);
        List<Chapter> chapterList = courseService.getAllChapterByCourseId(id);
        CommonResult commonResult = new CommonResult();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("detail", map);
        map1.put("chapter", chapterList);
        commonResult.setData(map1);
        return commonResult;
    }

    @GetMapping("/comment/{id}")
    public CommonResult getCommentByCourseId(@PathVariable Integer id) {
        List<Object> courseCommentList = courseService.getCommentByCourseId(id);
        CommonResult commonResult = new CommonResult();
        commonResult.setData(courseCommentList);
        return  commonResult;
    }
    @PostMapping("/comment")
    public CommonResult saveCommentByCourseId(@RequestBody CourseComment courseComment){

      CourseComment courseComment1=  courseService.saveComment(courseComment);
        CommonResult commonResult = new CommonResult();
        commonResult.setData(courseComment1);
        return  commonResult;

    }
}
