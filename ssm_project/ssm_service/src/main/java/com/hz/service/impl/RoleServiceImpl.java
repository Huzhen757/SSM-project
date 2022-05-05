package com.hz.service.impl;

import com.hz.dao.RoleDao;
import com.hz.domain.Permission;
import com.hz.domain.Role;
import com.hz.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao dao;

    @Override
    public List<Role> findAll() {
        return dao.findAll();
    }

    @Override
    public void save(Role role) {
        dao.save(role);
    }

    @Override
    public Role findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public void deleteRole(Integer id) {
        // 从中间表users_role中删除该角色
        dao.deleteFromUsers_RoleByRoleId(id);
        // 从中间表role_permission中删除该角色
        dao.deleteFromRole_PermissionByRoleId(id);
        // 从role表中删除该角色
        dao.deleteRole(id);
    }

    @Override
    public List<Permission> findOtherPermissions(Integer roleId) {
        return dao.findOtherPermissions(roleId);
    }

    @Override
    public void addPermissionToRole(Integer roleId, Integer[] perIds) {
        for(Integer perId:perIds){
            dao.addPermissionToRole(roleId, perId);
        }
    }
}
