package org.mybatis.practice.dao;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.practice.MybatisFactory;
import org.mybatis.practice.entity.User;
import org.mybatis.practice.mapper.UserMapper;

import java.util.List;

public class UserDao extends DaoBase {
    public Long registerNewUser(User user) {
        try (SqlSession session = MybatisFactory.getSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            if (mapper.queryByName(user.getName()) != null) {
                throw new BussinessException("该用户已注册");
            }
            mapper.insert(user);
            session.commit();
            return user.getId();
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

    public void delete(Long userId) {
        try (SqlSession session = MybatisFactory.getSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.delete(userId);
            session.commit();
        }
    }

    public List<User> listAll() {
        try (SqlSession session = MybatisFactory.getSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.listAll();
        }
    }
}
