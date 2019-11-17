package org.mybatis.practice.mapper;

import org.mybatis.practice.entity.User;

public interface UserMapper {
    Long insert(User user);

    User queryByName(String name);

    void setLogin(Long userId, Boolean isLogin);

    void delete(Long userId);
}
