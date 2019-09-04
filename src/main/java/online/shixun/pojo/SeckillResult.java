package online.shixun.pojo;

import java.io.Serializable;

public class SeckillResult implements Serializable {
	private static final long serialVersionUID = -3232832992723938L;
	private Long userId;
	private Long goodsId;
	private Integer status;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	
	
}
