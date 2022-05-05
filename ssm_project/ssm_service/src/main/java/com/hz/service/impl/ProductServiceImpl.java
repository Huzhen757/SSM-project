package com.hz.service.impl;

import com.hz.dao.ProductDao;
import com.hz.domain.Product;
import com.hz.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> findAll() throws Exception {
        List<Product> res = productDao.findAll();
        return res;
    }

    @Override
    public void save(Product product) throws Exception {
        productDao.save(product);
    }
}
