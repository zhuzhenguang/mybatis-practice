package org.mybatis.practice.dao;

public class BussinessException extends RuntimeException {
    BussinessException(String message) {
        super(message);
    }
}
