package cn.myfreecloud.store.dao.impl;

import cn.myfreecloud.store.dao.CategoryDao;
import cn.myfreecloud.store.domain.Category;
import cn.myfreecloud.store.utils.DataSourceUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    @Override
    public List<Category> findAll() throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select * from category";

        return qr.query(sql, new BeanListHandler<>(Category.class));
    }

    @Override
    public void save(Category category) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "insert into  category values(?,?)";
        qr.update(sql, category.getCid(), category.getCname());
    }

    @Override
    public Category findById(String cid) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select * from category where cid=?";
        return qr.query(sql, new BeanHandler<>(Category.class), cid);
    }

    @Override
    public void update(Category category) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "update category set cname=? where cid=?";
        qr.update(sql, category.getCname(), category.getCid());
    }

    @Override
    public void del(String cid) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "delete from category where cid=?";
        qr.update(sql, cid);
    }

    @Override
    public int findRel(String cid) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select count(*) from product where cid=?";
        return ((Long) qr.query(sql, new ScalarHandler(), cid)).intValue();
    }

}
