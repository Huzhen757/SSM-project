package com.hz.service;

import com.hz.domain.Permission;
import com.hz.domain.Role;

import java.util.List;

public interface RoleService {

    // 查询所有角色信息
    List<Role> findAll();

    // 增加角色信息
    void save(Role role);

    // 角色详情查询: 角色信息以及权限信息
    Role findById(Integer id);

    // 删除角色
    void deleteRole(Integer id);

    // 查询角色对应的没有获取到的权限信息
    List<Permission> findOtherPermissions(Integer roleId);

    // 给角色添加一些权限
    void addPermissionToRole(Integer roleId, Integer[] perIds);
}
