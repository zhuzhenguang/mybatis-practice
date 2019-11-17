package org.mybatis.practice.entity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
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
        items = new ArrayList<>();
    }

    public Order(Long userId) {
        this();
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

    public boolean unPaidWithin(int minutes) {
        LocalDateTime createdTime = Instant.ofEpochMilli(createdAt.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        return status == OrderStatus.Unpaid && LocalDateTime.now().minusMinutes(minutes).isAfter(createdTime);
    }
}
