package com.gg.service;

import com.gg.beans.Permission;

import java.util.List;

public interface PermissionService {
    Permission getAllPermission();

    Integer deletePermission(Integer id);

    Permission test(Integer id);

    Integer setRolePermission(Integer roleId, List<Integer> permissionIds);

//    List<String> getPermissionNameByUserId(Integer id);
}
