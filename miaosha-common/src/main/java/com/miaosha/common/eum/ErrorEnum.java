package com.miaosha.common.eum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ErrorEnum {

	SUCCESS(0, "操作成功"),
	UNKNOW_ERROR(100, "未知异常"),
	PARAMETER_ERROR(400, "请求参数错误"),
	NOT_FOUND_ERROR(404, "请求地址不存在"),
	INTERNAL_SERVER_ERROR(500, "系统内部错误"),
	
	JSON_ERROR(600, "Json格式错误"),
	REQUEST_METHOD_ERROR(601, "请求方式错误，不支持GET方法"),
	HTTP_MEDIA_ERROR(602, "请求数据类型错误"),
	VERSION_ERROR(700, "版本需要更新"),
	ACCESS_TOKEN_ERROR(1000, "登录令牌ACCESS_TOKEN错误"),
	REFRESH_TOKEN_ERROR(1001, "登录令牌REFRESH_TOKEN错误"),
	
	MAX_LOGIN_ERROR(2000, "密码错误次数过多"),
	CERTIFY_ERROR(2001, "请你先进行身份认证"),
	MOBILE_BIND_ERROR(2002, "请选绑定手机号码"),
	CAR_ERROR(2003, "您尚未添加车辆"),
	CAR_STATUS_ERROR(2005, "当前车辆离线"),
	USER_CERTIFY_ERROR(2004, "请你先进行实名认证"),
	SHOP_ERROR(2006, "你尚未添加门店");

	
	@Getter private int code;
	@Getter private String message;
}
