package com.hz.dao;

import com.hz.domain.Role;
import com.hz.domain.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    @Select("select * from users where username=#{username}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "phoneNum", column = "phoneNum"),
            @Result(property = "status", column = "status"),
            @Result(property = "roles", column = "id", javaType = java.util.List.class,
            many = @Many(select = "com.hz.dao.RoleDao.findRoleByUserId"))
    })
    UserInfo findByName(String username);

    // 查询用户
    @Select("select * from users")
    List<UserInfo> findAll();

    // 用户添加操作时，用户的密码在数据库中存储时需要加密 当我们进行登录时 由于加密需要修改用户登录操作
    @Insert("insert into users(email,username,password,phoneNum,status) values(#{email}, #{username}, #{password}, #{phoneNum}, #{status})")
    void save(UserInfo userInfo);

    // 查询用户详情
    @Select("select * from users where id=#{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "phoneNum", column = "phoneNum"),
            @Result(property = "status", column = "status"),
            // 根据用户id查询对应的roleId得到对应的角色
            @Result(property = "roles", column = "id", javaType = java.util.List.class,
            many = @Many(select = "com.hz.dao.RoleDao.findRoleByUserId"))

    })
    UserInfo findById(Integer id);

    // 根据用户id查询还没有的角色信息
    @Select("select * from role where id not in(select roleId from users_role where userId=#{id})")
    List<Role> findOtherRoles(Integer id);
    // 在中间表users_role中插入用户的角色信息
    @Insert("insert into users_role(userId,roleId) values(#{userId}, #{roleId})")
    void addRoleToUser(@Param("userId") Integer userId, @Param("roleId") Integer roleId);
}
