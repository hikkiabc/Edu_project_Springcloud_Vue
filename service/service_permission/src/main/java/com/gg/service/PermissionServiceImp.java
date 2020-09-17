package com.gg.service;

import com.gg.beans.BackUser;
import com.gg.beans.Permission;
import com.gg.beans.RolePermission;
import com.gg.mapper.PermissionMapper;
import com.gg.mapper.RolePermissionMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class PermissionServiceImp implements PermissionService {
    @Autowired
    PermissionMapper permissionMapper;
    @Autowired
    RolePermissionMapper rolePermissionMapper;

    @PersistenceContext
    private EntityManager em;
    public <T> Integer batchSave(Iterable<T> var1) {
        Iterator<T> iterator = var1.iterator();
        int index = 0;
        while (iterator.hasNext()){
            em.persist(iterator.next());
            index++;
            if (index % 500 == 0){
                em.flush();
                em.clear();
            }
        }
        if (index % 500 != 0){
            em.flush();
            em.clear();
        }
        return index;
    }
//    public <T> Integer batchInsert(List<T> list) {
//        if (!ObjectUtils.isEmpty(list)){
//            Integer res=0;
//            for (int i = 0; i < list.size(); i++) {
//                entityManager.persist(list.get(i));
//                res++;
//                if (i % 500 == 0) {
//                    entityManager.flush();
//                    entityManager.clear();
//                }
//            }
//
//            entityManager.flush();
//            entityManager.clear();
//            return res;
//        }
//        return 0;
//    }
    @Override
    public Permission getAllPermission() {
        List<Permission> permissionList = permissionMapper.findAll();
        for (Permission permission : permissionList) {
            if (permission.getPId() == 0) {
                permission.setLv("1");
                getPermissionWithChildren(permission, permissionList);
                return permission;
            }
        }
        return null;
    }

    private void getPermissionWithChildren(Permission permission, List<Permission> permissionList) {
        for (Permission permission1 : permissionList) {
            if (permission.getId().equals(permission1.getPId())) {
                permission1.setLv(Integer.valueOf(permission.getLv()) + 1 + "");
                permission.getChildren().add(permission1);
                getPermissionWithChildren(permission1, permissionList);
            }
        }
    }

    @Override
    public Integer deletePermission(Integer id) {

        Permission permission = permissionMapper.findById(id).get();
        List<Integer> list = new ArrayList<>();
        list.add(id);
        List<Integer> ids = getChildrenIds(permission, list);
        Integer integer = permissionMapper.deleteBatchById(ids);
        return integer;
    }

    @Override
    public Permission test(Integer id) {
        Permission permission = permissionMapper.findById(id).get();
        permission.setName("permissions");
        return permission;
    }

    @Override
    public Integer setRolePermission(Integer roleId, List<Integer> permissionIds) {
        List<RolePermission> rolePermissionList = new ArrayList<>();
        for (Integer permissionId : permissionIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissionList.add(rolePermission);
        }
        Integer i = batchSave(rolePermissionList);
        return i;
    }

//    @Override
//    public List<String> getPermissionNameByUserId(Integer id) {
//        List<Permission> selectPermissionList = null;
////        if(this.isSysAdmin(String.valueOf(id) )) {
////            //如果是超级管理员，获取所有菜单
////            selectPermissionList = permissionMapper.selectList(null);
////        } else {
////            selectPermissionList = permissionMapper.selectPermissionByUserId(id);
////        }
//       List<String> list=  permissionMapper.getPermissionNameByUserId(id);
//        System.out.println("selectPermissionList="+selectPermissionList);
//        List<Permission> permissionList = PermissionHelper.bulid(selectPermissionList);
//        System.out.println("permissionList="+permissionList);
//        List<JSONObject> result = MemuHelper.bulid(permissionList);
//        return result;
//    }

    private List<Integer> getChildrenIds(Permission permission, List<Integer> list) {
        List<Permission> byPId = permissionMapper.findBypId(permission.getId());
        if (byPId.size() > 0) {
            for (Permission permission1 : byPId) {
                list.add(permission1.getId());
                getChildrenIds(permission1, list);
            }
        }
        return list;
    }
    private boolean isSysAdmin(String userId) {
//        BackUser backUser = backUserService.getBackUserById(Integer.valueOf(userId));

//        if(null != backUser && "admin".equals(backUser.getUserName())) {
//            return true;
//        }
        return false;
    }

}
