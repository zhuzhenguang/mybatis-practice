package org.mybatis.practice.dao;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.practice.MybatisFactory;
import org.mybatis.practice.entity.Order;
import org.mybatis.practice.entity.OrderItem;
import org.mybatis.practice.mapper.OrderMapper;

import java.util.List;

public class OrderDao {
    public void createOrder(Order order, List<OrderItem> orderItems) {
        try (SqlSession session = MybatisFactory.getSession()) {
            OrderMapper mapper = session.getMapper(OrderMapper.class);
            mapper.insert(order);

            for (OrderItem orderItem : orderItems) {
                orderItem.setOrderId(order.getId());
            }

            mapper.insertItems(orderItems);
            session.commit();
        }
    }

    public Order queryById(Long id) {
        try (SqlSession session = MybatisFactory.getSession()) {
            OrderMapper mapper = session.getMapper(OrderMapper.class);
            return mapper.queryById(id);
        }
    }
}
