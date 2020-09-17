package com.gg.service;

import com.gg.beans.*;
import com.gg.feign.UserFeign;
import com.gg.mapper.*;
import com.gg.utils.CommonResult;
import com.gg.vo.CourseAndDescVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class CourseServiceImp implements CourseService {
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    ChapterMapper chapterMapper;
    @Autowired
    SubChapterMapper subChapterMapper;
    @Autowired
    SubjectMapper subjectMapper;
    @Autowired
    CourseCommentMapper courseCommentMapper;
@Autowired
    UserFeign userFeign;
    @Override
    public Course save(CourseAndDescVO courseAndDescVO) {

        Course course = new Course();
        CourseDesc courseDesc = new CourseDesc();
        BeanUtils.copyProperties(courseAndDescVO, course);
        BeanUtils.copyProperties(courseAndDescVO, courseDesc);
        course.setCourseDesc(courseDesc);
        courseDesc.setCourse(course);
        courseDesc.setId(courseAndDescVO.getDescId());
        Course save = courseMapper.save(course);

        return save;
    }

    @Override
    public Chapter saveChapter(Chapter chapter) {

        return chapterMapper.save(chapter);
    }

    @Override
    public List<Chapter> getAllChapterByCourseId(String courseId) {
        List<Chapter> chapterLv1List = chapterMapper.findByCourseIdOrderBySort(courseId);
        List<SubChapter> chapterLv2List = subChapterMapper.findByCourseIdOrderBySort(courseId);
        for (Chapter chapterLv1 : chapterLv1List) {
            for (SubChapter chapterLv2 : chapterLv2List) {
                if (chapterLv2.getChapterId().equals(chapterLv1.getId())) {
                    chapterLv1.getChildren().add(chapterLv2);
                }
            }
        }

        return chapterLv1List;
    }

    @Override
    public Course getCourseById(String id) {
        Optional<Course> course = courseMapper.findById(Integer.valueOf(id));
        Course course1 = course.get();

        return course1;
    }

    @Override
    public void deleteChapterById(String id) {
        Integer count = subChapterMapper.findCountByChapterId(id);
        if (count > 0) throw new RuntimeException("has subChapters");
        chapterMapper.CusDeleteChapterById(id);
    }

    @Override
    public SubChapter addSubChapter(SubChapter subChapter) {

        return subChapterMapper.save(subChapter);
    }

    @Override
    public void deleteSubChapterById(String id) {
        subChapterMapper.CusDeleteChapterById(id);
    }

    @Override
    public Object getCoursePublishVO(String id) {
        Object coursePublishVOByCourseId = courseMapper.getCoursePublishVOByCourseId(id);
        return coursePublishVOByCourseId;
    }


    @Override
    public void publishCourse(String id) {
        courseMapper.publishCourse(id);
    }

    @Override
    public void deleteCourseById(String id) {

        courseMapper.deleteById(Integer.valueOf(id));
        courseMapper.deleteByIdCus(id);
    }

    @Override
    public Page<Course> findAllByConditions(String keyword, String min, String max, PageRequest pageRequest) {

        return courseMapper.findAllByConditions(keyword, min, max, pageRequest);
    }

    @Override
    public Page<Course> getFrontCourseByCondition(String lv1, String lv2, PageRequest pageRequest) {

        Page<Course> coursePage = courseMapper.getFrontCourseByCondition(lv1, lv2, pageRequest);
        return coursePage;
    }

    @Override
    public List<SubjectWithChildren> getAllSubject() {
        List<Subject> subjectList = subjectMapper.findAll();
        List<SubjectWithChildren> resList = new ArrayList<>();
        for (Subject subject : subjectList) {
            if (subject.getPid() == 0) {
                SubjectWithChildren subjectWithChildren = new SubjectWithChildren();
                BeanUtils.copyProperties(subject, subjectWithChildren);
                resList.add(subjectWithChildren);
                for (Subject subject1 : subjectList) {
                    if (subject1.getPid() == subject.getId()) {
                        SubjectWithChildren subjectWithChildren1 = new SubjectWithChildren();
                        BeanUtils.copyProperties(subject1, subjectWithChildren1);
                        subjectWithChildren.getChildren().add(subjectWithChildren1);
                    }
                }
            }
        }
        return resList;
    }

    @Override
    public Map getCourseDetailById(String id) {
        return courseMapper.getCourseDetailById(id);
    }

    @Override
    public List<Object> getCommentByCourseId(Integer id) {
        List<CourseComment> courseCommentList =
                courseCommentMapper.findByCourseId(id,Sort.by("createDate").descending());
        Set<Integer> set=new HashSet<>();
        List<CourseComment> res = new ArrayList<>();
        List<Object> resList=new ArrayList<>();
        for (CourseComment courseComment : courseCommentList) {
            set.add(courseComment.getUserId());
            if (courseComment.getPid()==null){
                Integer id1 = courseComment.getId();
                for (CourseComment comment : courseCommentList) {
                    if (id1.equals(comment.getPid())){
                        courseComment.getChildren().add(comment);
                    }
                }
                res.add(courseComment);
            }
        }
        List users = userFeign.getUsersByIds(set);
        resList.add(res);
        resList.add(users);
        return resList;
    }

    @Override
    public CourseComment saveComment(CourseComment courseComment) {
        return courseCommentMapper.save(courseComment);
    }

}
