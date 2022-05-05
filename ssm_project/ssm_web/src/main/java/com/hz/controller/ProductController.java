package com.hz.controller;

import com.hz.DateStringEditor;
import com.hz.domain.Product;
import com.hz.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;

    // 查询所有产品
    @RequestMapping("/findAll.do")
    public ModelAndView findAll() throws Exception{
        ModelAndView mv = new ModelAndView();
        List<Product> res = service.findAll();
        mv.addObject("productList", res);
        mv.setViewName("product-list1");
        return mv;
    }

    // 产品添加
    @RequestMapping("/save.do")
    public String save(Product product) throws Exception{
        service.save(product);
        return "redirect:findAll.do";
    }

//    @InitBinder
//    public void initBinder(WebDataBinder binder){
//        // 使用propertyEditor来解决日期格式问题
//        // 1. 在控制器中 2. 需要创建一个实现PropertiesEditor接口的实体类(重写setAsText方法)
//        binder.registerCustomEditor(Date.class, new DateStringEditor());
//    }
}
