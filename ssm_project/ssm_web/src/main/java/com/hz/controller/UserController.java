package com.hz.controller;

import com.hz.domain.Role;
import com.hz.domain.UserInfo;
import com.hz.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private userService service;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll(){
        ModelAndView mv = new ModelAndView();
        List<UserInfo> res = service.findAll();
        mv.addObject("userList", res);
        mv.setViewName("user-list");
        return mv;
    }

    @RequestMapping("/save.do")
    public String save(UserInfo userInfo){
        service.save(userInfo);
        return "redirect:findAll.do";
    }

    // 用户详情查询: 主要是查询用户关联的角色以及角色所关联的权限 响应给Controller 控制器将UserInfo信息保存
    // 并且跳转到user-show.jsp页面
    @RequestMapping("/findById.do")
    public ModelAndView findById(@RequestParam(required = true,name = "id")Integer id){
        ModelAndView mv = new ModelAndView();
        UserInfo userInfo = service.findById(id);
        mv.addObject("user", userInfo);
        mv.setViewName("user-show1");
        return mv;
    }

    // 先查询某个用户是否有对应的角色信息 查询当前用户在角色表中不存在的角色信息
    @RequestMapping("/findUserByIdAndAllRole.do")
    public ModelAndView findUserByIdAndAllRole(@RequestParam(required = true,name = "id")Integer id){
        UserInfo user = service.findById(id);
        // 查询当前用户所没有的角色信息 返回的是一个Role集合
        List<Role> roles = service.findOtherRoles(id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("user", user);
        mv.addObject("roleList", roles);
        mv.setViewName("user-role-add");
        return mv;
    }
    // 关联用户与角色信息主要就是在user_role中间表中插入数据即可(网页传递给控制器的参数有userId和ids即为可插入角色的id)
    @RequestMapping("/addRoleToUser.do")
    public String addRoleToUser(@RequestParam(name = "userId",required = true)Integer userId,
                              @RequestParam(name = "ids",required = true)Integer[] roleId){
        service.addRoleToUser(userId, roleId);
        return "redirect:findAll.do";
    }



}
