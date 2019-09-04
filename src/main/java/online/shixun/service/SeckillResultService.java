package online.shixun.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import online.shixun.dao.SeckillResultDao;
import online.shixun.pojo.SeckillResult;

@Service
public class SeckillResultService {

	@Autowired
	SeckillResultDao seckillResultDao;
	
	//把秒杀结果放入数据库中
	
	public void insertResult(SeckillResult seckillResult) {
		seckillResultDao.insertSeckillResult(seckillResult);
	}
	
	
}
