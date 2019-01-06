package cn.myfreecloud.store.service;

import cn.myfreecloud.store.domain.User;

public interface UserService {
	User findById(String id);

	void regist(User user) throws Exception;

	void active(String code)throws Exception;

	User login(String username, String password)throws Exception;
}
