package org.mybatis.practice.dao;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.practice.MybatisFactory;
import org.mybatis.practice.entity.User;
import org.mybatis.practice.mapper.UserMapper;

public class UserDao extends DaoBase {
    public Long registerNewUser(User user) {
        try (SqlSession session = MybatisFactory.getSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            Long id = mapper.insert(user);
            session.commit();
            return id;
        }
    }

    public User queryUser(String name) {
        try (SqlSession session = MybatisFactory.getSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.queryByName(name);
        }
    }

    public void login(Long userId) {
        try (SqlSession session = MybatisFactory.getSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.setLogin(userId, true);
            session.commit();
        }
    }

    public void logout(Long userId) {
        try (SqlSession session = MybatisFactory.getSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.setLogin(userId, false);
            session.commit();
        }

    }
}
