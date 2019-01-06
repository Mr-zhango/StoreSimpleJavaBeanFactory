package cn.myfreecloud.store.service.impl;

import cn.myfreecloud.store.dao.ProductDao;
import cn.myfreecloud.store.domain.PageBean;
import cn.myfreecloud.store.domain.Product;
import cn.myfreecloud.store.service.ProductService;
import cn.myfreecloud.store.utils.BeanFactory;

import java.util.List;

public class ProductServiceImpl implements ProductService {
	private ProductDao productDao= BeanFactory.getInstance(ProductDao.class);
	@Override
	public List<Product> findNews() throws Exception {
		
		return productDao.findNews();
	}

	@Override
	public List<Product> findHots() throws Exception {
		return productDao.findHots();
	}

	@Override
	public PageBean<Product> findByPageWithCate(String cid, int pageNumber, int pageSize) throws Exception {
		PageBean<Product> pageBean = new PageBean<>();
		//相办法 准备好五样数据
		pageBean.setPageNumber(pageNumber);
		pageBean.setPageSize(pageSize);
		
		List<Product> data=productDao.findByPageWithCate(cid,(pageNumber-1)*pageSize,pageSize);
		pageBean.setData(data);
		
		int total=productDao.findTOtalWithCate(cid);
		pageBean.setTotal(total);
		
		return pageBean;
	}

	@Override
	@Log
	public Product findbyId(String pid) throws Exception {
		return productDao.findbyId(pid);
	}

	@Override
	public PageBean<Product> findByPage(int pageNumber, int pageSize) throws Exception {
		PageBean<Product> pageBean = new PageBean<>();
		//相办法 准备好五样数据
		pageBean.setPageNumber(pageNumber);
		pageBean.setPageSize(pageSize);
		
		List<Product> data=productDao.findByPage((pageNumber-1)*pageSize,pageSize);
		pageBean.setData(data);
		
		int total=productDao.findTotal();
		pageBean.setTotal(total);
		
		return pageBean;
	}

	@Override
	public void save(Product product) throws Exception {
		productDao.save(product);
	}

}
