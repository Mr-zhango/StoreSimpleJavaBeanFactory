package cn.myfreecloud.store.dao.impl;

import cn.myfreecloud.store.dao.UserDao;
import cn.myfreecloud.store.domain.User;
import cn.myfreecloud.store.utils.DataSourceUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    @Override
    public User findById(String id) {
        return null;
    }

    @Override
    public void save(User u) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());

        /**
         * private String uid;
         private String username;
         private String password;

         private String name;
         private String email;
         private String telephone;

         private Date birthday;
         private String gender;
         private int state;//1  激活   0 未激活

         private String code;
         private String content;//备注
         */
        String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?,?)";
        qr.update(sql, u.getUid(), u.getUsername(), u.getPassword(),
                u.getName(), u.getEmail(), "",
                u.getBirthday(), u.getGender(), u.getState(),
                u.getCode(), u.getContent());

    }

    @Override
    public User findByCode(String code) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select * from user where code=?";
        return qr.query(sql, new BeanHandler<>(User.class), code);
    }

    @Override
    public void update(User u) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
        /**
         * private String uid;
         private String username;
         private String password;

         private String name;
         private String email;
         private String telephone;

         private Date birthday;
         private String gender;
         private int state;//1  激活   0 未激活

         private String code;
         private String content;//备注
         */

        String sql = "update user  set password=?,name=?,email=?,birthday=?,gender=?, state=? ,content=?  where uid=?";

        qr.update(sql, u.getPassword(), u.getName(), u.getEmail(),
                u.getBirthday(), u.getGender(), u.getState(), u.getContent(), u.getUid());

    }

    @Override
    public User findByUserNameAndPwd(String username, String password) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select * from user where username=? and password=?";
        return qr.query(sql, new BeanHandler<>(User.class), username, password);
    }

}
