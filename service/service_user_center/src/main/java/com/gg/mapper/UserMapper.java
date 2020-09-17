package com.gg.mapper;

import com.gg.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserMapper extends JpaRepository<User,Integer> {

     User getByPhoneAndPassword(String phone, String password);
//     Integer findByPhone(String phone);
     Integer countByPhone(String phone);
     @Query(value="select * from user where id in ?1",nativeQuery=true)
     List<User> getUsersByIds(List ids);
@Query(value="select count(*) from user where date(create_date) =?1",nativeQuery=true)
    Integer getRegisterCountByDate(String date);
}
