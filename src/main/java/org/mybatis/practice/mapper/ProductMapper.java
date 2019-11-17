package org.mybatis.practice.mapper;

import org.mybatis.practice.entity.Product;

import java.util.List;

public interface ProductMapper {
    Long insert(Product product);

    Product queryById(Long id);

    List<Product> listAll();

    void update(Product product);

    void delete(Long id);
}
