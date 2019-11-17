package org.mybatis.practice.entity;

import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

public class Order {
    private Long id;
    private Long userId;
    private OrderStatus status;
    private Date createdAt;
    private List<OrderItem> items;
    private User user;

    public Order() {
    }

    public Order(Long userId) {
        this.userId = userId;
        this.status = OrderStatus.Unpaid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double totalPrice() {
        return items.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getCount()).sum();
    }
}
