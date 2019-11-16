package org.mybatis.practice.dao;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.practice.MybatisFactory;
import org.mybatis.practice.entity.User;
import org.mybatis.practice.mapper.UserMapper;

public class UserDao extends DaoBase {
    public void registerNewUser(User user) {
        try (SqlSession session = MybatisFactory.getSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            Long id = mapper.insert(user);
            session.commit();
        }
    }

    public User queryUser(String name) {
        return getMapper(UserMapper.class).queryByName(name);
    }
}
