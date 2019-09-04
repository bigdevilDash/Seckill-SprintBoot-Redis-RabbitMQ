package online.shixun.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import online.shixun.dao.OrderDao;
import online.shixun.pojo.OrderInfo;
import online.shixun.pojo.SeckillOrder;
import online.shixun.pojo.vo.GoodsVo;


@Service
public class OrderService {
	
	@Autowired
	OrderDao orderDao;
	//通过用户id和商品id获取秒杀订单信息
	public SeckillOrder getseckillOrderByUserIdGoodsId(long userId, long goodsId) {
		return orderDao.getSeckillOrderByUserIdGoodsId(userId, goodsId);
	}

	//创建秒杀订单
	@Transactional
	public OrderInfo createOrder(long userId, GoodsVo goods) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCreateDate(new Date());
		orderInfo.setDeliveryAddrId(0L);
		orderInfo.setGoodsCount(1);
		orderInfo.setGoodsId(goods.getId());
		orderInfo.setGoodsName(goods.getGoodsName());
		orderInfo.setGoodsPrice(goods.getSeckillPrice());
		orderInfo.setOrderChannel(1);
		orderInfo.setStatus(0);
		orderInfo.setUserId(userId);
		long orderId = orderDao.insert(orderInfo);
		SeckillOrder seckillOrder = new SeckillOrder();
		seckillOrder.setGoodsId(goods.getId());
		seckillOrder.setOrderId(orderId);
		seckillOrder.setUserId(userId);
		orderDao.insertSeckillOrder(seckillOrder);
		return orderInfo;
	}
	
	//查找订单信息
	public OrderInfo searchOrder(Long userId,Long goodsId) {
		return orderDao.searchOrder(userId,goodsId);
	}
	
}
