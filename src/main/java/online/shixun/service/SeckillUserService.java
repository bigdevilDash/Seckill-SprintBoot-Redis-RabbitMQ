package online.shixun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.shixun.dao.SeckillUserDao;
import online.shixun.exception.GlobalException;
import online.shixun.pojo.SeckillUser;
import online.shixun.pojo.vo.LoginVo;
import online.shixun.result.CodeMsg;


@Service
public class SeckillUserService {
	
	@Autowired
	SeckillUserDao seckillUserDao;
	//根据id获取用户信息
	public SeckillUser getById(long id) {
		return seckillUserDao.getById(id);
	}

	//登录验证
	public SeckillUser login(LoginVo loginVo) {
		if(loginVo == null) {
			throw new GlobalException(CodeMsg.SERVER_ERROR);
		}
		
		String mobile = loginVo.getMobile();
		String formPass = loginVo.getPassword();
		//根据输入的手机号获取数据库的用户信息
		SeckillUser user = getById(Long.parseLong(mobile));
		if(user == null) {
			throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
		}
		//验证密码
		if(!formPass.equals(user.getPassword())) {
			throw new GlobalException(CodeMsg.PASSWORD_ERROR);
		}
		return user;
	}
	
}
