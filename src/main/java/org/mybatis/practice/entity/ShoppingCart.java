package org.mybatis.practice.entity;

import java.util.Date;
import java.util.List;

public class ShoppingCart {
    private Long id;
    private Long userId;
    private Date createdAt;
    private List<ShoppingCartItem> items;

    public ShoppingCart() {
    }

    public ShoppingCart(Long userId) {
        this.userId = userId;
    }

    public ShoppingCart(Long id, Long userId) {
        this.id = id;
        this.userId = userId;
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

    public List<ShoppingCartItem> getItems() {
        return items;
    }

    public void setItems(List<ShoppingCartItem> items) {
        this.items = items;
    }
}
