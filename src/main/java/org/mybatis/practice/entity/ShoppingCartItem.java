package org.mybatis.practice.entity;

import java.util.Date;

public class ShoppingCartItem {
    private Long id;
    private Integer count;
    private Long productId;
    private Date createdAt;
    private Long shoppingCartId;

    public ShoppingCartItem() {
    }

    public ShoppingCartItem(Long productId, int count) {
        this.productId = productId;
        this.count = count;
    }

    public ShoppingCartItem(Long shoppingCartId, Long productId, int count) {
        this(productId, count);
        this.shoppingCartId = shoppingCartId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }
}
