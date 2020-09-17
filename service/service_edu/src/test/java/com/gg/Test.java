package com.gg;

import com.gg.beans.*;
import com.gg.mapper.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceEdu8001.class)
public class Test {

    @Autowired
ClassForStudentMapper classForStudentMapper;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    EmpMapper empMapper;
    @Autowired
    ProjectMapper projectMapper;
  @org.junit.Test
  @Transactional
//  @Rollback(false)
  public void JPA(){
//
//        User user = new User();
//        user.setUsername("name2");
//
//        userMapper.save(user);
//      Optional<User> byId = userMapper.findById(1);
//      System.out.println(byId.get());
//      List<User> users = userMapper.findAll(Sort.by("id").descending());

//      articleRepository.findAll(Sort.by("createTime"));
//      articleRepository.findAll(Sort.by("author").ascending()
//              .and(Sort.by("createTime").descending()));
//      第一个findAll方法是按照createTime的升序进行排序
//      第一个findAll方法是按照author的升序排序，再按照createTime的降序进行排序

//      Page<User> users = userMapper.findAll(PageRequest.of(0, 2, Sort.Direction.DESC, "id"));
//      long totalElements = users.getTotalElements();
//      List<User> byIdBetweenOrderByIdDesc = userMapper.findByIdBetweenOrderByIdDesc(0, 1);
//      System.out.println(byIdBetweenOrderByIdDesc);
//      Student student = new Student();
//      student.setStudentName("name1");
//      ClassForStudent classForStudent = new ClassForStudent();
//      classForStudent.setClassName("name1");
//      classForStudent.setId(1);
//      classForStudentMapper.save(classForStudent);
//      student.setClassForStudent(classForStudent);
//      studentMapper.save(student);
//      List<Student> studentList = studentMapper.findAll();
//      for (Student student : studentList) {
//          System.out.println(student.getClassForStudent().getClassName());
//      }
//
      Emp emp = new Emp();

      emp.seteName("name4");
            empMapper.save(emp);
      ArrayList<Emp> list = new ArrayList<>();
      list.add(emp);

//      project.getEmpList().add(emp);

  }
  @org.junit.Test
  public void test2(){
      Set<Object> set = new HashSet<>();
        set.add(1);
        set.add(1);
        set.add(3);
        set.add(2);
      System.out.println(set);
  }}
