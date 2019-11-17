package org.mybatis.practice.dao;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.practice.MybatisFactory;
import org.mybatis.practice.entity.ShoppingCart;
import org.mybatis.practice.entity.ShoppingCartItem;
import org.mybatis.practice.mapper.ShoppingCartMapper;

import java.util.List;

public class ShoppingCartDao {
    public void add(Long productId, Long userId, int count) {
        try (SqlSession session = MybatisFactory.getSession()) {
            ShoppingCartMapper mapper = session.getMapper(ShoppingCartMapper.class);

            ShoppingCart shoppingCart = mapper.queryByUserId(userId);
            if (shoppingCart == null) {
                shoppingCart = new ShoppingCart(userId);
                mapper.insert(shoppingCart);
            }
            mapper.insertItem(new ShoppingCartItem(shoppingCart.getId(), productId, count));
            session.commit();
        }
    }

    public ShoppingCart queryByUserId(Long userId) {
        try (SqlSession session = MybatisFactory.getSession()) {
            ShoppingCartMapper mapper = session.getMapper(ShoppingCartMapper.class);
            return mapper.queryByUserId(userId);
        }
    }
}
