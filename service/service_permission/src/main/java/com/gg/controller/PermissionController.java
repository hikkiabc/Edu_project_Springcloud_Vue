package com.gg.controller;

import com.gg.beans.Permission;
import com.gg.beans.RolePermission;
import com.gg.mapper.PermissionMapper;
import com.gg.service.PermissionService;
import com.gg.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@Transactional
//@RestController
@Controller
@RequestMapping("/permission")
public class PermissionController {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    PermissionService permissionService;

    @GetMapping
    public CommonResult getAllPermission(){
        Permission permissionList= permissionService.getAllPermission();
        CommonResult commonResult = new CommonResult();
        commonResult.setData(permissionList);

        return commonResult;
    }
    @PostMapping
    public CommonResult deletePermission(Integer id){
       Integer res= permissionService.deletePermission(id);
        CommonResult commonResult = new CommonResult();
        commonResult.setData(res);
        return commonResult;
    }
    @PostMapping("/test")
    public Object test( HttpServletResponse response) throws IOException {
//        em.persist(id);
        response.getWriter().write("Servlet02");
        System.out.println(1);
        return "null";
    }
    @PostMapping("/setrolepermission")
    public CommonResult setRolePermission(@RequestParam("roleId") Integer roleId,@RequestParam("permissionIds") List<Integer> permissionIds){
        System.out.println(permissionIds);
      Integer res=  permissionService.setRolePermission(roleId,permissionIds);
        CommonResult commonResult = new CommonResult();
        commonResult.setData(res);
        return commonResult;
    }

}
