package com.hz.service;

import com.hz.domain.Permission;

import java.util.List;

public interface PermissionService {

    // 查询所有权限信息
    List<Permission> findAll();

    // 增加权限信息
    void save(Permission permission);

    // 删除权限
    void deletePermission(Integer id);


}
