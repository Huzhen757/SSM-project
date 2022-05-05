package com.hz.dao;

import com.hz.domain.Permission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionDao {

    // 根据角色id去查询中间表Role_permission得到permission表的id 再查询得到Permission信息
    @Select("select * from permission where id in (select permissionId from role_permission where roleId=#{id})")
    public List<Permission> findPermissionByRoleId(Integer id);

    // 直接根据permissionId查询permission表得到权限信息
    @Select("select * from permission")
    List<Permission> findAll();

    // 增加权限信息
    @Insert("insert into permission(permissionName, url) values(#{permissionName}, #{url})")
    void save(Permission permission);

    // 删除权限: 先删除中间表role_permission中的值 再去删除permission表中的权限
    @Delete("delete from role_permission where permissionId=#{id}")
    void deleteFromRole_PermissionByPermissionId(Integer id);
    @Delete("delete from Permission where id=#{id}")
    void deletePermission(Integer id);
}
