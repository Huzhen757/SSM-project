package com.hz.dao;

import com.hz.domain.Permission;
import com.hz.domain.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao {
    // 根据用户id查询得到角色信息
    @Select("select * from role where id in (select roleId from users_role where userId=#{id})")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "roleName", column = "roleName"),
            @Result(property = "roleDesc", column = "roleDesc"),
            // 根据角色id查询中间表role_permissions得到角色对应的权限信息
            @Result(property = "permissions", column = "id", javaType = java.util.List.class,
            many = @Many(select = "com.hz.dao.PermissionDao.findPermissionByRoleId"))
    })
    List<Role> findRoleByUserId(Integer id);

    // 角色查询
    @Select("select * from role")
    List<Role> findAll();

    // 角色添加
    @Insert("insert into role(roleName, roleDesc) values(#{roleName}, #{roleDesc})")
    void save(Role role);

    // 角色的详情查询: 角色的权限也需要查询
    @Select("select * from role where id=#{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "roleName", column = "roleName"),
            @Result(property = "roleDesc", column = "roleDesc"),
            // 根据中间表查询角色对应的权限信息
            @Result(property = "permissions", column = "id", javaType = java.util.List.class,
            many = @Many(select = "com.hz.dao.PermissionDao.findPermissionByRoleId"))
    })
    Role findById(Integer id);

    // 角色删除: 考虑角色表与用户表的关联 角色表与权限表的关联
    @Update("delete from role where id=#{id}")
    void deleteRole(Integer id);
    @Update("delete from users_role where roleId=#{id}")
    void deleteFromUsers_RoleByRoleId(Integer id);
    @Delete("delete from role_permission where roleId=#{id}")
    void deleteFromRole_PermissionByRoleId(Integer id);

    // 查询角色对应的还没获取到的权限信息 返回的是一个list集合
    @Select("select * from permission where id not in(select permissionId from role_permission where roleId=#{roleId})")
    List<Permission> findOtherPermissions(Integer roleId);
    // 给角色添加一些权限(只需要在中间表role_permission中添加权限即可)
    @Insert("insert into role_permission(permissionId, roleId) values(#{id2}, #{id1})")
    void addPermissionToRole(@Param("id1") Integer roleId, @Param("id2") Integer perId);
}
