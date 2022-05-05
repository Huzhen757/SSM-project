package com.hz.dao;

import com.hz.domain.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao {

    @Select("select * from Product")
    List<Product> findAll() throws Exception;

    @Insert("insert into product(productNum,productName,cityName,departureTime,productPrice,productDesc,productStatus) values(#{productNum},#{productName},#{cityName},#{departureTime},#{productPrice},# {productDesc},#{productStatus})")
    void save(Product product) throws Exception;

    @Select("select * from Product where id=#{id}")
    Product findById(Integer id);

}
