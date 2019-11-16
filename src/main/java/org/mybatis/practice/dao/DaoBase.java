package org.mybatis.practice.dao;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.practice.MybatisFactory;

abstract class DaoBase {
    <M> M getMapper(Class<M> mapperClass) {
        SqlSession session = MybatisFactory.getSession();
        return session.getMapper(mapperClass);
    }
}
