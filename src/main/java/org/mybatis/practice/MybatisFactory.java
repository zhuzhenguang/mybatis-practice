package org.mybatis.practice;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisFactory {
    private final static String resource = "mybatis-config.xml";
    private static SqlSessionFactory factory = CreateFactory();

    private static SqlSessionFactory CreateFactory() {
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    public static SqlSession getSession() {
        return factory.openSession();
    }
}
