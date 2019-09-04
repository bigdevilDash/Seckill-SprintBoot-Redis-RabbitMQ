package online.shixun.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import online.shixun.pojo.OrderInfo;
import online.shixun.pojo.SeckillOrder;
import online.shixun.pojo.SeckillResult;
import online.shixun.pojo.vo.GoodsVo;
import online.shixun.result.CodeMsg;
import online.shixun.service.GoodsService;
import online.shixun.service.OrderService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	@Autowired
	GoodsService goodsService;

	@Autowired
	OrderService orderService;

	static SeckillResult seckillResult = null;

//	@Autowired
//	AmqpTemplate amqpTemplate;

//	amqpTemplate.convertAndSend("seckill",seckillUser);

//    @RequestMapping("/info")
//    @ResponseBody
//    public Result<SeckillUser> info(Model model,SeckillUser user) {
//        return Result.success(user);
//    }

	@RequestMapping(value = "/check")
	@ResponseBody
	public int redisCheck(HttpServletRequest request) {
		int data = 2;
        String userId=request.getParameter("userId");
        
        String goodsID=request.getParameter("goodsId");
        long goodsId = Long.parseLong(goodsID);
        
		seckillResult = (SeckillResult) redisTemplate.opsForValue().get(userId);

		if (seckillResult != null) {

			if (seckillResult.getStatus() == 1 && seckillResult.getGoodsId() == goodsId) {
				data = 1;

				return data;
			} else if (seckillResult.getStatus() == 0 && seckillResult.getGoodsId() == goodsId) {
				data = 0;

				return data;
			} else {
                data=-1;
				return data;
			}

		}

		return data;

	}

	@RequestMapping(value = "/success")
	public String success(Model model) {
		Long userId = seckillResult.getUserId();
		Long goodsId = seckillResult.getGoodsId();

		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		OrderInfo orderInfo = orderService.searchOrder(userId, goodsId);

		model.addAttribute("orderInfo", orderInfo);
		model.addAttribute("goods", goods);
		return "order_detail";
	}

	@RequestMapping(value = "/fail")
	public String fail(Model model) {
		model.addAttribute("errmsg", CodeMsg.REPEATE_seckill.getMsg());
		return "seckill_fail";
	}

	@RequestMapping(value = "/fail2")
	public String fail2(Model model) {
		model.addAttribute("errmsg", CodeMsg.SECKILL_OVER.getMsg());
		return "seckill_fail";
	}
	
	@RequestMapping(value = "/fail3")
	public String fail3(Model model) {
		model.addAttribute("errmsg", CodeMsg.SECKILL_ING.getMsg());
		return "seckill_fail";
	}

}
