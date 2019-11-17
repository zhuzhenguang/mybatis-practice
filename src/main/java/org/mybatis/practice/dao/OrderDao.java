package org.mybatis.practice.dao;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.practice.MybatisFactory;
import org.mybatis.practice.entity.Order;
import org.mybatis.practice.entity.OrderItem;
import org.mybatis.practice.entity.OrderStatus;
import org.mybatis.practice.mapper.OrderMapper;

import java.util.List;

public class OrderDao {
    private static final int CHECK_MINUTES = 30;

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

    public Order queryById(Long orderId) {
        try (SqlSession session = MybatisFactory.getSession()) {
            OrderMapper mapper = session.getMapper(OrderMapper.class);
            return mapper.queryById(orderId);
        }
    }

    public void pay(Long orderId) {
        try (SqlSession session = MybatisFactory.getSession()) {
            OrderMapper mapper = session.getMapper(OrderMapper.class);
            mapper.updateStatus(orderId, OrderStatus.Paid);
            session.commit();
        }
    }

    public void cancel(Long orderId) {
        try (SqlSession session = MybatisFactory.getSession()) {
            OrderMapper mapper = session.getMapper(OrderMapper.class);
            mapper.updateStatus(orderId, OrderStatus.Canceled);
            session.commit();
        }
    }

    public void checkAndCancel(Long orderId) {
        try (SqlSession session = MybatisFactory.getSession()) {
            OrderMapper mapper = session.getMapper(OrderMapper.class);
            if (mapper.queryById(orderId).unPaidWithin(CHECK_MINUTES)) {
                mapper.updateStatus(orderId, OrderStatus.Canceled);
            }
            session.commit();
        }
    }

    public List<Order> queryByUser(Long userId) {
        try (SqlSession session = MybatisFactory.getSession()) {
            OrderMapper mapper = session.getMapper(OrderMapper.class);
            return mapper.queryByUser(userId);
        }
    }
}
