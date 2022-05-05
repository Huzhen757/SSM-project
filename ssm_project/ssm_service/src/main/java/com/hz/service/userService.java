package com.hz.service;

import com.hz.domain.Role;
import com.hz.domain.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;

/*
UserDetails是一个接口，我们可以认为UserDetails作用是于封装当前进行认证的用户信息，但由于其是一个
接口，所以我们可以对其进行实现，也可以使用Spring Security提供的一个UserDetails的实现类User来完成操作

必须让自定义接口去继承UserDetailsService接口
 */

public interface userService extends UserDetailsService {


    List<UserInfo> findAll();

    void save(UserInfo userInfo);

    UserInfo findById(Integer id);

    // 根据用户id查询用户没有的权限
    List<Role> findOtherRoles(Integer id);

    // 插入用户没有的角色
    void addRoleToUser(Integer userId, Integer[] roleId);

}
