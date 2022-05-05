package com.hz.service;

import com.hz.domain.Orders;

import java.util.List;

public interface OrderService {

    // 分页查询订单信息
    List<Orders> findAll(int page, int size);

    Orders findById(Integer id);

}
