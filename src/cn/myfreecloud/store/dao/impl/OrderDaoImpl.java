package cn.myfreecloud.store.dao.impl;

import cn.myfreecloud.store.dao.OrderDao;
import cn.myfreecloud.store.domain.Order;
import cn.myfreecloud.store.domain.OrderItem;
import cn.myfreecloud.store.domain.Product;
import cn.myfreecloud.store.utils.DataSourceUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class OrderDaoImpl implements OrderDao {

    @Override
    public void save(OrderItem oi) throws SQLException {
        QueryRunner qr = new QueryRunner();
        /**
         * //订单项id
         private String itemid;
         //订单项数量
         private Integer count;
         //订单项小计
         private Double subtotal;

         private String pid;
         private String oid;
         */
        String sql = "insert into orderitem values(?,?,?,?,?)";
        qr.update(DataSourceUtil.getConnection(), sql, oi.getItemid(), oi.getCount(), oi.getSubtotal(),
                oi.getPid(), oi.getOid());

    }

    @Override
    public void save(Order o) throws SQLException {
        QueryRunner qr = new QueryRunner();
        /**
         * // 订单id
         private String oid;
         // 订单时间
         private Date ordertime;
         // 订单金额
         private Double total;

         // 订单状态
         private Integer state;// 订单状态 0:未付款 1:已付款 2:已发货 3.已完成
         // 收获地址
         private String address;
         // 收货人姓名
         private String name;

         // 收获人电话
         private String telephone;

         private String uid;

         */
        String sql = "insert into orders values(?,?,?,?,?,?,?,?) ";
        qr.update(DataSourceUtil.getConnection(), sql, o.getOid(), o.getOrdertime(), o.getTotal(),
                o.getState(), o.getAddress(), o.getName(),
                o.getTelephone(), o.getUid());

    }

    @Override
    public int findTotal(String uid) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select count(*) from orders  where uid=?";
        return ((Long) qr.query(sql, new ScalarHandler(), uid)).intValue();
    }

    @Override
    public List<Order> findMyOrders(String uid, int startIndex, int pageSize) throws Exception {
        //查询当前登录人的订单列表 分页
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select * from orders where uid=? order by ordertime desc limit ?,? ";
        List<Order> orders = qr.query(sql, new BeanListHandler<>(Order.class), uid, startIndex, pageSize);
        //获取只是纯纯的订单信息  需要订单中包含着订单项列表
        for (Order order : orders) {
            //获取订单id
            String oid = order.getOid();
            sql = "select * from orderitem oi,product p where p.pid=oi.pid and oid=?";
            List<Map<String, Object>> listmap = qr.query(sql, new MapListHandler(), oid);
            //想要是订单项信息 和订单项信息 包含着商品
            for (Map<String, Object> map : listmap) {
                //将map中数据 封装成一个orderItem 和一个商品
                //创建一个订单项对象
                OrderItem orderItem = new OrderItem();
                //创建一商品对象
                Product product = new Product();
                //封装订单项的值
                BeanUtils.populate(orderItem, map);
                //封装商品的值
                BeanUtils.populate(product, map);
                //给订单项这个实体对象封装商品信息
                orderItem.setProduct(product);
                //给订单封装items集合(订单的详情集合)
                order.getItems().add(orderItem);
            }

        }
        return orders;
    }

    @Override
    public Order findByIdWithItems(String oid) throws Exception {
        //查询当前登录人的订单列表 分页
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select * from orders where oid=? ";
        Order order = qr.query(sql, new BeanHandler<>(Order.class), oid);
        sql = "select * from orderitem oi,product p where p.pid=oi.pid and oid=?";
        List<Map<String, Object>> listmap = qr.query(sql, new MapListHandler(), oid);
        //想要是订单项信息 和订单项信息 包含着商品
        for (Map<String, Object> map : listmap) {
            //将map中数据 封装成一个orderItem 和一个商品i
            OrderItem orderItem = new OrderItem();
            Product product = new Product();
            BeanUtils.populate(orderItem, map);
            BeanUtils.populate(product, map);
            orderItem.setProduct(product);
            order.getItems().add(orderItem);
        }
        return order;
    }

    @Override
    public Order findById(String oid) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
        String sql = "select * from orders where oid=? ";
        Order order = qr.query(sql, new BeanHandler<>(Order.class), oid);
        return order;
    }

    @Override
    public void update(Order order) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
        /**
         * private String oid;//订单id
         private int state;//未付款 已付款 已发货
         private String address;//收货人地址
         private String name;//收货人名字
         private String telephone;//收获人电话
         */
        String sql = "update orders set state=? ,address=?,name=?,telephone=? where oid=?";
        qr.update(sql, order.getState(), order.getAddress(), order.getName(), order.getTelephone(), order.getOid());
    }

}
