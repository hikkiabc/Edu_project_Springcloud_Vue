package com.gg.mapper;

import com.gg.beans.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TeacherMapper extends JpaRepository<Teacher, Integer>, JpaSpecificationExecutor<Teacher> {

    @Query(value = "select * from teacher" + " where (name like concat('%',?1,'%') or ?1 is null)"
            + " and (start_time between ?2 and ?3 or ?2 is null)", nativeQuery = true)
    Page<Teacher> findAllBySpecifications(String keyword, String max, String min, Pageable pageable);

    @Query(value = "update teacher set name=:#{#t.name} where id=:#{#t.id}", nativeQuery = true)
    @Modifying
    @Transactional
    Integer update(Teacher t);

    @Modifying
    @Query(value = "delete from teacher where id=?1", nativeQuery = true)
    Integer deleteByIdCus(Integer id);


}
