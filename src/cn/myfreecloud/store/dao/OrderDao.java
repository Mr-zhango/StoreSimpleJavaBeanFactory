package cn.myfreecloud.store.dao;

import cn.myfreecloud.store.domain.Order;
import cn.myfreecloud.store.domain.OrderItem;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {

    void save(OrderItem oi) throws SQLException;

    void save(Order order) throws SQLException;

    int findTotal(String uid) throws SQLException;

    List<Order> findMyOrders(String uid, int i, int pageSize) throws Exception;

    Order findByIdWithItems(String oid) throws Exception;

    Order findById(String oid) throws Exception;

    void update(Order order) throws Exception;

}
