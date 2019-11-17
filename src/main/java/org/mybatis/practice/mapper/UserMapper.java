package org.mybatis.practice.mapper;

import org.mybatis.practice.entity.User;

import java.util.List;

public interface UserMapper {
    Long insert(User user);

    User queryByName(String name);

    List<User> listAll();

    void setLogin(Long userId, Boolean isLogin);

    void delete(Long userId);
}
