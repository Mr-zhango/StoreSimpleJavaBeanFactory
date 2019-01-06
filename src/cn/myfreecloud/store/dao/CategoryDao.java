package cn.myfreecloud.store.dao;

import cn.myfreecloud.store.domain.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {

    List<Category> findAll() throws SQLException;

    void save(Category category) throws SQLException;

    Category findById(String cid) throws SQLException;

    void update(Category category) throws SQLException;

    void del(String cid) throws SQLException;

    int findRel(String cid) throws SQLException;

}
