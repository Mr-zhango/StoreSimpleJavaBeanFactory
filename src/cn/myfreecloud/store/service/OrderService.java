package cn.myfreecloud.store.service;

import cn.myfreecloud.store.domain.Order;
import cn.myfreecloud.store.domain.PageBean;

public interface OrderService {

	void save(Order order) throws Exception;

	PageBean<Order> findMyOrdersByPage(String uid, int pageNumber, int pageSize)throws Exception;

	Order findByIdWithItems(String oid)throws Exception;

	Order findById(String oid)throws Exception;

	void update(Order order)throws Exception;

}
