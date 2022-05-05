package com.hz.controller;

import com.hz.domain.Permission;
import com.hz.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService service;

    // 查询所有权限信息
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(){
        ModelAndView mv = new ModelAndView();
        List<Permission> res = service.findAll();
        mv.addObject("permissionList", res);
        mv.setViewName("permission-list");
        return mv;
    }

    // 增加权限信息
    @RequestMapping("/save.do")
    public String save(Permission permission){
        service.save(permission);
        return "redirect:findAll.do";
    }

    // 删除权限
    @RequestMapping("/deletePermission.do")
    public String deletePermission(Integer id){
        service.deletePermission(id);
        return "redirect:findAll.do";

    }

}
