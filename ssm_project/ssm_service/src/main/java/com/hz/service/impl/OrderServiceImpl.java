package com.hz.service.impl;

import com.github.pagehelper.PageHelper;
import com.hz.dao.OrdersDao;
import com.hz.domain.Orders;
import com.hz.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersDao dao;

    @Override
    public List<Orders> findAll(int page, int size) {
        // 参数pageNum表示页码值 pageSize表示每页显示的条数
        PageHelper.startPage(page, size);
        return dao.findAll();
    }

    @Override
    public Orders findById(Integer id) {
        return dao.findById(id);
    }
}
