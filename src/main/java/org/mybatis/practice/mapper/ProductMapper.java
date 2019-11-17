package org.mybatis.practice.mapper;

import org.mybatis.practice.entity.Product;

public interface ProductMapper {
    Long insert(Product product);

    Product queryById(Long id);
}
