package cn.myfreecloud.store.service.impl;

import cn.myfreecloud.store.dao.OrderDao;
import cn.myfreecloud.store.domain.Order;
import cn.myfreecloud.store.domain.OrderItem;
import cn.myfreecloud.store.domain.PageBean;
import cn.myfreecloud.store.service.OrderService;
import cn.myfreecloud.store.utils.BeanFactory;
import cn.myfreecloud.store.utils.DataSourceUtil;

import java.util.List;

public class OrderServiceImpl implements OrderService {
	private OrderDao orderDao= BeanFactory.getInstance(OrderDao.class);
	@Override
	public void save(Order order) throws Exception {
		//保存订单 和保存订单项 要不全成功要不全失败
		//开启事务
		try {
		    //开启事务
			DataSourceUtil.beginTransaction();
			//保存订单的方法
			orderDao.save(order);
			for (OrderItem oi : order.getItems()) {
			    //保存订单项的方法
			    orderDao.save(oi);
			}
			DataSourceUtil.commitAndClose();
		} catch (Exception e) {
			e.printStackTrace();
			//如果产生异常的话回滚事务
			DataSourceUtil.rollbackAndClose();
		}
	}
	@Override
	public PageBean<Order> findMyOrdersByPage(String uid, int pageNumber, int pageSize) throws Exception {
		PageBean<Order> pb=new PageBean<>();
		pb.setPageNumber(pageNumber);
		pb.setPageSize(pageSize);
		
		int total=orderDao.findTotal(uid);
		pb.setTotal(total);
		List<Order> data=orderDao.findMyOrders(uid,(pageNumber-1)*pageSize,pageSize);
		pb.setData(data);
		return pb;
	}
	@Override
	public Order findByIdWithItems(String oid) throws Exception {
		
		
		return orderDao.findByIdWithItems(oid);
	}
	@Override
	public Order findById(String oid) throws Exception {
		return orderDao.findById(oid);
	}
	@Override
	public void update(Order order) throws Exception {
		orderDao.update(order);
	}

}
