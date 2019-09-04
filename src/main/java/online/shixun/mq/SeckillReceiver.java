package online.shixun.mq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;




import online.shixun.pojo.SeckillOrder;
import online.shixun.pojo.SeckillResult;
import online.shixun.pojo.vo.GoodsVo;
import online.shixun.service.GoodsService;
import online.shixun.service.OrderService;
import online.shixun.service.SeckillResultService;
import online.shixun.service.SeckillService;


@Component
@RabbitListener(queues= {"seckillResult"})
public class SeckillReceiver {
	@Autowired
	GoodsService goodsService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	SeckillService seckillService;
	
    @Autowired
    SeckillResultService seckillResultService;
	
    @Autowired
	RedisTemplate<String, Object> redisTemplate;

	@RabbitHandler
	public void receiver(SeckillResult seckillResult) {
		try {
		
		Long goodsId=seckillResult.getGoodsId();
		Long userId=seckillResult.getUserId();
		String sb=userId.toString();
		
		
    	//判断库存
    	GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
    	int stock = goods.getStockCount();
    	
    	if(stock <= 0) {
    		seckillResult.setStatus(-1);
    		
    		seckillResultService.insertResult(seckillResult);
    		redisTemplate.opsForValue().set(sb, seckillResult);
    		return;
//    		model.addAttribute("errmsg", CodeMsg.SECKILL_OVER.getMsg());
//    		return "seckill_fail";
    	}
    	////通过用户id和商品id获取秒杀订单信息，判断订单是否存在，是否已经秒杀到了
    	SeckillOrder order = orderService.getseckillOrderByUserIdGoodsId(userId, goodsId);
    	if(order != null) {
    		seckillResult.setStatus(0);
    		
    		seckillResultService.insertResult(seckillResult);
    		redisTemplate.opsForValue().set(sb, seckillResult);
    		return;

    	}
    	//减库存 下订单 写入秒杀订单
    	
    	seckillService.seckill(userId, goods);
    	
    	seckillResult.setStatus(1);
    	seckillResultService.insertResult(seckillResult);
    	
    	redisTemplate.opsForValue().set(sb, seckillResult);
    	
//    	model.addAttribute("orderInfo", orderInfo);
//    	model.addAttribute("goods", goods);
    	
//      return "order_detail";
		}catch(Exception e) {
			e.printStackTrace();
		}
    	

	}
	
	
}
