package com.gg.mapper;

import com.gg.beans.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionMapper extends JpaRepository<Permission,Integer> {
    List<Permission> findBypId(Integer pId);
    @Modifying
    @Query(value="delete from permission where id in ?1",nativeQuery=true)
    Integer deleteBatchById(List ids);
}
