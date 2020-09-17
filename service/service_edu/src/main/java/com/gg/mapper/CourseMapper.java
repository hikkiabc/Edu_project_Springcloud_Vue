package com.gg.mapper;

import com.gg.beans.Course;
import com.gg.vo.CourseAndDescVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface CourseMapper extends JpaRepository<Course, Integer> {
    @Query(value = "select course.id, course.name as courseName, course.poster," +
            "course_desc.course_desc," +
            "subject1.title as subjectLv1, subject2.title as subjectLv2," +
            "teacher.name as teacherName,course.price from course left join " +
            "course_desc on course_desc.course_id=course.id left join teacher" +
            " on teacher.id=course.teacher_id left join " +
            "subject subject1 on subject1.id=course.subject_lv1id left join" +
            " subject subject2 on subject2.id=course.subject_lv2id where course.id=?1", nativeQuery = true)
//    @Query(value="select * from course where id in(71,73)",nativeQuery=true)
    Map<String, Object> getCoursePublishVOByCourseId(String id);

    @Modifying
    @Query(value = "update course set status='publish' where id=?1", nativeQuery = true)
    void publishCourse(String id);


    @Modifying
    @Query(value = "delete chapter,sub_chapter from chapter left join sub_chapter on" +
            " sub_chapter.course_id=chapter.course_id where chapter.course_id =:id", nativeQuery = true)
    void deleteByIdCus(@Param("id") String id);

    @Query(value = "select * from course" + " where (name like concat('%',?1,'%') or ?1 is null)" +
            " and (create_time between ?2 and ?3 or ?2 is null)", nativeQuery = true)
    Page<Course> findAllByConditions(String keyword, String min, String max, Pageable pageable);

    List<Course> getByTeacherId(String id);

    @Query(value = "select * from course" + " where (subject_lv1id=?1 or ?1 is null)"
            + " and (subject_lv2id=?2 or ?2 is null)", nativeQuery = true)
    Page<Course> getFrontCourseByCondition(String subLv1Id, String subLv2Id, PageRequest pageRequest);

    @Query(value = "select course.name as courseName,course.price,course_desc.course_desc," +
            " teacher.name as teacherName, subject1.title as " +
            "subject1, subject2.title as subject2 from " +
            "course left join course_desc on course_desc.id = course.id" +
            " left join subject subject1 on subject1.id=course.subject_lv1id" +
            " left join subject subject2 on  subject2.id=course.subject_lv2id" +
            " left join teacher on teacher.id=course.teacher_id where course.id=?1", nativeQuery = true)
    Map getCourseDetailById(String id);
}
