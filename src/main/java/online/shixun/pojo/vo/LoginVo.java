package online.shixun.pojo.vo;

import javax.validation.constraints.NotNull;

import online.shixun.validator.IsMobile;


public class LoginVo {
	
	@NotNull
	@IsMobile
	private String mobile;
	
	@NotNull
	private String password;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "LoginVo [mobile=" + mobile + ", password=" + password + "]";
	}
}
