package com.hz.controller;

import com.hz.domain.Permission;
import com.hz.domain.Role;
import com.hz.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService service;

    // 查询所有角色信息
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(){
        ModelAndView mv = new ModelAndView();
        List<Role> res = service.findAll();
        mv.addObject("roleList", res);
        mv.setViewName("role-list");
        return mv;
    }
    // 增加角色信息
    @RequestMapping("/save.do")
    public String save(Role role){
        service.save(role);
        return "redirect:findAll.do";
    }

    // 角色的详情查询: 角色信息以及对象的权限信息
    @RequestMapping("/findById.do")
    public ModelAndView findById(@RequestParam(required = true,name = "id") Integer id){
        ModelAndView mv = new ModelAndView();
        Role role = service.findById(id);
        mv.setViewName("role-show");
        mv.addObject("role", role);
        return mv;
    }

    // 角色删除
    @RequestMapping("/deleteRole.do")
    public String deleteRole(@RequestParam(required = true,name = "id") Integer id){
        service.deleteRole(id);
        return "redirect:findAll.do";
    }

    // 查询某个角色是否有权限信息: 返回角色信息以及还有获取到的权限集合
    @RequestMapping("/findRoleByIdAndAllPermission.do")
    public ModelAndView findRoleByIdAndAllPermission(@RequestParam(required = true,name = "id") Integer roleId){
        Role role = service.findById(roleId);
        List<Permission> permissions = service.findOtherPermissions(roleId);
        ModelAndView mv = new ModelAndView();
        mv.addObject("role", role);
        mv.addObject("permissionList", permissions);
        mv.setViewName("role-permission-add");
        return mv;
    }
    // 关联角色与权限信息主要就是在role_permission中间表中插入数据即可(页面传递给控制器的参数有roleId和ids即没有获取到的权限id)
    @RequestMapping("/addPermissionToRole.do")
    public String addPermissionToRole(@RequestParam(required = true,name = "roleId")Integer roleId,
                                      @RequestParam(required = true,name = "ids")Integer[] perIds){
        service.addPermissionToRole(roleId, perIds);
        return "redirect:findAll.do";
    }
}
