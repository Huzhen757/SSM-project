package com.hz.service.impl;

import com.hz.dao.PermissionDao;
import com.hz.domain.Permission;
import com.hz.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao dao;

    @Override
    public List<Permission> findAll() {
        return dao.findAll();
    }

    @Override
    public void save(Permission permission) {
        dao.save(permission);
    }

    @Override
    public void deletePermission(Integer id) {
        dao.deleteFromRole_PermissionByPermissionId(id);
        dao.deletePermission(id);
    }

}
