package cn.myfreecloud.store.service.impl;

import cn.myfreecloud.store.dao.CategoryDao;
import cn.myfreecloud.store.domain.Category;
import cn.myfreecloud.store.exception.ProductHasRelCategoryException;
import cn.myfreecloud.store.service.CategoryService;
import cn.myfreecloud.store.utils.BeanFactory;
import cn.myfreecloud.store.utils.RedisUtil;
import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = BeanFactory.getInstance(CategoryDao.class);

    @Override
    @Log
    public String list() throws Exception {
		/*List<Category> list=categoryDao.findAll();
		String result  = JSONArray.fromObject(list).toString();
		return result;*/
        //////////////////////////////////使用redis作为缓存////////////////////////
        //先获取连接
        Jedis connection = RedisUtil.getConnection();
        //先去查询redis
        String categories = connection.get("categories");
        //如果redis没有数据
        if (categories == null) {
            //查询数据库
            List<Category> list = categoryDao.findAll();
            System.out.println("查询数据库了");
            String result = JSONArray.fromObject(list).toString();
            //查出数据 不要直接返回 而是将此数据 放入redis 再返回
            connection.set("categories", result);
            connection.close();
            return result;
        } else {
            //等再一次访问来的 那么redis是有数据
            //从redis取出数据返回
            connection.close();
            return categories;
        }
    }

    @Override
    public void save(Category category) throws Exception {
        categoryDao.save(category);
        clear();
    }

    private void clear() {
        //先获取连接
        Jedis connection = RedisUtil.getConnection();
        //先去查询redis
        connection.del("categories");
        connection.close();
    }

    @Override
    public Category findById(String cid) throws Exception {
        return categoryDao.findById(cid);
    }

    @Override
    public void update(Category category) throws Exception {
        categoryDao.update(category);
        //清缓存
        clear();
    }

    @Override
    public void del(String cid) throws Exception {
        //查询是否有所关联
        //先去商品表中查询 是否当前分类被引用
        int count = categoryDao.findRel(cid);
        if (count == 0) {
            //没有引用
            categoryDao.del(cid);
            clear();
        } else {
            //通知上层代码  这里发生了一个已经预料的错误
            throw new ProductHasRelCategoryException();

        }
    }

}
