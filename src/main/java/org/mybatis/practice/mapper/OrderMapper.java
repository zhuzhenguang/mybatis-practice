package org.mybatis.practice.mapper;

import org.apache.ibatis.annotations.Param;
import org.mybatis.practice.entity.Order;
import org.mybatis.practice.entity.OrderItem;

import java.util.List;

public interface OrderMapper {
    void insert(Order order);

    void insertItems(@Param("orderItems") List<OrderItem> orderItems);

    Order queryById(Long id);
}
