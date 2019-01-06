package cn.myfreecloud.store.service;

import cn.myfreecloud.store.domain.PageBean;
import cn.myfreecloud.store.domain.Product;

import java.util.List;
public interface ProductService {

	List<Product> findNews()throws Exception;

	List<Product> findHots()throws Exception;

	PageBean<Product> findByPageWithCate(String cid, int pageNumber, int pageSize)throws Exception;

	Product findbyId(String pid)throws Exception;

	PageBean<Product> findByPage(int pageNumber, int pageSize)throws Exception;

	void save(Product product)throws Exception;

}
