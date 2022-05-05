package com.hz.dao;

import com.hz.domain.Member;
import com.hz.domain.Orders;
import com.hz.domain.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersDao {

    @Select("select * from orders")
    @Results({
            @Result(id = true,property = "id",column = "id"), // 订单的id和数据库表中的id
            @Result(property = "orderNum", column = "orderNum"),
            @Result(property = "orderTime", column = "orderTime"),
            @Result(property = "orderStatus", column = "orderStatus"),
            @Result(property = "peopleCount", column = "peopleCount"),
            @Result(property = "payType", column = "payType"),
            @Result(property = "orderDesc", column = "orderDesc"),
            // property中的product为实体类中的属性，column为表中的列名，多个订单对应一个产品(
            // 使用one注解来根据id查找对应的product信息)
            @Result(property = "product", column = "productId", javaType = Product.class,
                    one = @One(select = "com.hz.dao.ProductDao.findById"))
    })
    List<Orders> findAll();

    @Select("select * from orders where id=#{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "orderNum", column = "orderNum"),
            @Result(property = "orderTime", column = "orderTime"),
            @Result(property = "orderStatus", column = "orderStatus"),
            @Result(property = "peopleCount", column = "peopleCount"),
            @Result(property = "payType", column = "payType"),
            @Result(property = "orderDesc", column = "orderDesc"),
            @Result(property = "product", column = "productId", javaType = Product.class,
                    one = @One(select = "com.hz.dao.ProductDao.findById")),
            @Result(property = "member", column = "memberId", javaType = Member.class,
            one = @One(select = "com.hz.dao.MemberDao.findById")),
            // 根据订单编号去查询order_traveller表得到多个travellerId 再去查询traveller表得到多个traveller 返回的是一个list集合
            @Result(property = "travellers", column = "id", javaType = java.util.List.class,
            many = @Many(select = "com.hz.dao.TravellerDao.findByOrdersId"))
    })
    Orders findById(Integer id);

}
