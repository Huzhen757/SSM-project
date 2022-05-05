package com.hz.controller;

import com.github.pagehelper.PageInfo;
import com.hz.domain.Orders;
import com.hz.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrderService service;

    // 查询全部订单信息(未分页)
//    @RequestMapping("/findAll.do")
//    public ModelAndView findAll(){
//        List<Orders> res = service.findAll();
//        ModelAndView mv = new ModelAndView();
//        // 在orderList.jsp中遍历的属性名
//        mv.addObject("ordersList", res);
//        // 跳转到哪个jsp页面
//        mv.setViewName("orders-list");
//        return mv;
//    }

    // 查询订单信息(分页)
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page",required = true,defaultValue = "1")int page,
                                @RequestParam(name = "size",required = true,defaultValue = "5")int size){

        ModelAndView mv = new ModelAndView();
        List<Orders> res = service.findAll(page, size);
        // pageInfo就是一个分页bean
        PageInfo pi = new PageInfo(res);
        mv.addObject("pageInfo", pi);
        mv.setViewName("orders-page-list");
        return mv;
    }

    // 订单查询时需要查询产品，旅客以及会员信息
    // 订单与产品是多对一关系
    // 订单与会员是多对一关系
    // 订单与旅客是多对多关系(通过中间表order_traveller关联查询)
    @RequestMapping("/findById.do")
    public ModelAndView findById(@RequestParam(name = "id", required = true) Integer id){
        ModelAndView mv = new ModelAndView();
        Orders res = service.findById(id);
        mv.addObject("orders", res);
        mv.setViewName("orders-show");
        return mv;
    }
}
