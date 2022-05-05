package com.hz.controller;

import com.hz.domain.SysLog;
import com.hz.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/syslog")
public class SysLogController {

    @Autowired
    private SysLogService service;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll(){
        ModelAndView mv = new ModelAndView();
        List<SysLog> res = service.findAll();
        mv.addObject("sysLogs", res);
        mv.setViewName("syslog-list");
        return mv;
    }
}
