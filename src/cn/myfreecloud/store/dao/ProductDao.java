package cn.myfreecloud.store.dao;

import cn.myfreecloud.store.domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    List<Product> findNews() throws SQLException;

    List<Product> findHots() throws SQLException;

    List<Product> findByPageWithCate(String cid, int i, int pageSize) throws SQLException;

    int findTOtalWithCate(String cid) throws SQLException;

    Product findbyId(String pid) throws SQLException;

    List<Product> findByPage(int i, int pageSize) throws SQLException;

    int findTotal() throws SQLException;

    void save(Product product) throws SQLException;

}
