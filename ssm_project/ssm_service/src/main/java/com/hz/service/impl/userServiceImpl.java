package com.hz.service.impl;

import com.hz.PasswordEncoderUtils;
import com.hz.dao.UserDao;
import com.hz.domain.Role;
import com.hz.domain.UserInfo;
import com.hz.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */
@Service("userService")
@Transactional
public class userServiceImpl implements userService {

    @Autowired
    private UserDao dao;

     @Autowired
     private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        UserInfo userInfo = dao.findByName(username);
        // 处理自己的用户对象 获取其username以及password 封装成UserDetails
//        User user = new User(userInfo.getUsername(),
//                "{noop}" + userInfo.getPassword(), // 没有进行加密处理 需要在获取密码前加上前缀"noop"
//                getAuthority(userInfo.getRoles()));
        // 根据查询到的用户的status来进一步判断是否能登陆(status=0不能登录 =1可以登录)
        // 如果在spring-security中配置了加密方式 获取密码不需要再加上前缀"noop"
        User user = new User(userInfo.getUsername(),
                  userInfo.getPassword(), userInfo.getStatus()==0?false:true,
                 true, true, true, getAuthority(userInfo.getRoles()));
        return user;
    }

    // 返回一个list集合，集合中的元素是角色描述
    public List<SimpleGrantedAuthority> getAuthority(List<Role> roles){
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for(Role role : roles){
            list.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        }
        return list;
    }

    @Override
    public List<UserInfo> findAll() {
        return dao.findAll();
    }

    @Override
    public void save(UserInfo userInfo) {
        // 对密码进行处理 再保存用户信息
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
//        userInfo.setPassword(PasswordEncoderUtils.encodePassword(userInfo.getPassword()));
        dao.save(userInfo);
    }

    @Override
    public UserInfo findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public List<Role> findOtherRoles(Integer id) {
        return dao.findOtherRoles(id);
    }

    @Override
    public void addRoleToUser(Integer userId, Integer[] roleId) {
        for(Integer id:roleId){
            dao.addRoleToUser(userId, id);
        }
    }

}
