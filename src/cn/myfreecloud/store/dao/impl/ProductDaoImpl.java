package cn.myfreecloud.store.dao.impl;

import cn.myfreecloud.store.constant.Constant;
import cn.myfreecloud.store.dao.ProductDao;
import cn.myfreecloud.store.domain.Product;
import cn.myfreecloud.store.utils.DataSourceUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;

import java.util.List;

public class ProductDaoImpl implements ProductDao {

	@Override
	public List<Product> findNews() throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
		String sql="select * from product order by pdate desc limit 12";
		return qr.query(sql, new BeanListHandler<>(Product.class));
	}

	@Override
	public List<Product> findHots() throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
		String sql="select * from product where is_hot=? order by pdate desc   limit 12";
		return qr.query(sql, new BeanListHandler<>(Product.class), Constant.PRODUCT_IS_HOT);
	}

	@Override
	public List<Product> findByPageWithCate(String cid, int StartIndex, int pageSize) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
		String sql="select * from product where  cid=? order by pdate desc  limit ?,?";
		return qr.query(sql, new BeanListHandler<>(Product.class),cid,StartIndex,pageSize);
	}

	@Override
	public int findTOtalWithCate(String cid) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
		String sql="select count(*) from product where cid=?";
		return ((Long) qr.query(sql, new ScalarHandler(),cid)).intValue();
	}

	@Override
	public Product findbyId(String pid) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
		String sql="select * from product where pid=?";
		return qr.query(sql, new BeanHandler<>(Product.class),pid);
	}

	@Override
	public List<Product> findByPage(int startindex, int pageSize) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
		String sql="select * from product order by pdate desc limit ?,?";
		return qr.query(sql, new BeanListHandler<>(Product.class),startindex,pageSize);
	}

	@Override
	public int findTotal() throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
		String sql="select count(*) from product ";
		return ((Long) qr.query(sql, new ScalarHandler())).intValue();
	}

	@Override
	public void save(Product p) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
		/**
         *
        private String pid;//id
        private String pname;//名字
        private Double market_price;//市场价

        private Double shop_price;//商城价
        private String pimage;//图片
        private Date pdate;//日期

        private Integer is_hot;  //是否热门  1:热门    0:不热门
        private String pdesc;//描述
        private Integer pflag;	//是否下架    1:下架	0:未下架
        private String cid;

		 */
		String sql="insert into product values(?,?,?,?,?,?,?,?,?,?) ";
		qr.update(sql, p.getPid(),p.getPname(),p.getMarket_price(),
				p.getShop_price(),p.getPimage(),p.getPdate(),
				p.getIs_hot(),p.getPdesc(),p.getPflag(),
				p.getCid());
	}


	@Override
	public void delProductById(String pid) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSource());
		String sql="DELETE FROM product WHERE pid = ?";
		qr.update(sql, pid);
	}
}
