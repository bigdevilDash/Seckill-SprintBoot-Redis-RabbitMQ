package online.shixun.controller;



import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import online.shixun.pojo.SeckillResult;



@Controller
@RequestMapping("/seckill")
public class SeckillController {



	@Autowired
	AmqpTemplate amqpTemplate;
	
	//点击秒杀后
    @RequestMapping("/do_seckill")
    public String list(Model model,@RequestParam("userId") long userId,@RequestParam("goodsId") long goodsId) {
    	if(userId == 0) {
    		return "login";
    	}
        
    	model.addAttribute("userId", userId);
    	model.addAttribute("goodsId",goodsId);
    	
    	SeckillResult seckillResult=new SeckillResult();
    	seckillResult.setUserId(userId);
    	seckillResult.setGoodsId(goodsId);
    	amqpTemplate.convertAndSend("seckillResult",seckillResult);
    	return "wait";
    	

    }
}
