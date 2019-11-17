package org.mybatis.practice.mapper;

import org.mybatis.practice.entity.ShoppingCart;
import org.mybatis.practice.entity.ShoppingCartItem;

public interface ShoppingCartMapper {
    ShoppingCart queryByUserId(Long userId);

    Long insert(ShoppingCart shoppingCart);

    void insertItem(ShoppingCartItem item);
}
