package com.hz.service;

import com.hz.domain.Product;
import java.util.List;

public interface ProductService {

    List<Product> findAll() throws Exception;

    void save(Product product) throws Exception;
}
