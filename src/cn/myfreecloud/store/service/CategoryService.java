package cn.myfreecloud.store.service;

import cn.myfreecloud.store.domain.Category;

public interface CategoryService {

    /**
     * 查询分类信息
     * @return
     * @throws Exception
     */
	String list() throws Exception;

    /**
     * 保存分类信息
     * @param category
     * @throws Exception
     */
	void save(Category category) throws Exception;

    /**
     * 商品详情页方法,根据商品id查询商品详情信息
     * @param cid
     * @return
     * @throws Exception
     */
	Category findById(String cid)throws Exception;

    /**
     *
     * @param category
     * @throws Exception
     */

	void update(Category category)throws Exception;

    /**
     *
     * @param cid
     * @throws Exception
     */
	void del(String cid)throws Exception;

}
