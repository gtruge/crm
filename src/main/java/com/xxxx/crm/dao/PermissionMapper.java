package com.xxxx.crm.dao;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.vo.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission,Integer> {

    //通过角色Id查询权限记录
    Integer countPermissionByRoleId(Integer roleId);
    //通过角色Id删除权限记录
    void deletePermissionByRoleId(Integer roleId);
    //查询角色拥有的所有资源Id的集合
    List<Integer> queryRoleHasModuleIdsByRoleId(Integer roleId);

    //通过用户Id查询对应的资源列表（资源权限码）
    List<String> queryUserHasRoleHasPermissionByUserId(Integer userId);

    //通过资源Id查询权限记录
    Integer countPermissionByModuleId(Integer id);

    //通过资源id删除权限记录
    void deletePermissionByModuleId(Integer id);
}