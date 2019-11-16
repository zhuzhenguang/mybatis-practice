package org.mybatis.practice.mapper;

import org.mybatis.practice.entity.User;

public interface UserMapper {
    Long insert(User user);

    User queryByName(String name);
}
