package cn.myfreecloud.store.service.impl;
import cn.myfreecloud.store.domain.User;
import cn.myfreecloud.store.service.UserService;

public class MockUserService implements UserService {
	public User findById(String id) {
		return  new User();
	}

	@Override
	public void regist(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void active(String code) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User login(String username, String password) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
