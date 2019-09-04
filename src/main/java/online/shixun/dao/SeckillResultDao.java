package online.shixun.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import online.shixun.pojo.SeckillResult;



@Mapper
public interface SeckillResultDao {

	@Insert("insert into seckill_result (user_id, goods_id, status)values(#{userId}, #{goodsId}, #{status})")
	public int insertSeckillResult(SeckillResult seckillResult);

}
