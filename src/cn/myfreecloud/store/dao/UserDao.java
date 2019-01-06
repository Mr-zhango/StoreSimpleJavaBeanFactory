package cn.myfreecloud.store.dao;

import cn.myfreecloud.store.domain.User;

import java.sql.SQLException;

public interface UserDao {
    User findById(String id);

    void save(User user) throws SQLException;

    User findByCode(String code) throws SQLException;

    void update(User user) throws SQLException;

    User findByUserNameAndPwd(String username, String password) throws SQLException;
}
