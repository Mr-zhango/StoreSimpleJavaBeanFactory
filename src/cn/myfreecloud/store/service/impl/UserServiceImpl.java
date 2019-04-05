package cn.myfreecloud.store.service.impl;

import cn.myfreecloud.store.constant.Constant;
import cn.myfreecloud.store.dao.UserDao;
import cn.myfreecloud.store.domain.User;
import cn.myfreecloud.store.exception.UserActiveJokeExcption;
import cn.myfreecloud.store.service.UserService;
import cn.myfreecloud.store.utils.BeanFactory;
import cn.myfreecloud.store.utils.MailUtil;

public class UserServiceImpl implements UserService {
	private UserDao userDao = BeanFactory.getInstance(UserDao.class);
	@Override
	public User findById(String id) {
		return userDao.findById(id);
	}
	@Override
	public void regist(User user) throws Exception {
		//将数据保存到数据库
		userDao.save(user);
		//发送电子邮件
		String msg="恭喜你注册成为商城一员,<a href='http://www.myfreecloud.cn/store/user?md=active&code="+user.getCode()+"' >点我去激活</a>";
		MailUtil.sendMail(user.getEmail(), msg);
	}
	@Override
	public void active(String code) throws Exception {
		//先查查有木有这个人 
		User user=userDao.findByCode(code);
		if(user!=null){
			//有才激活
			user.setState(Constant.USER_IS_ACTIVE);
			userDao.update(user);
		}else{
			//没有? 刻意捣乱的  直接抛出异常
			throw new UserActiveJokeExcption();
		}
	}
	@Override
	public User login(String username, String password) throws Exception {
		return userDao.findByUserNameAndPwd(username,password);
	}

	
	
	
	
	
	
}
