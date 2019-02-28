package com.miaosha.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CommonEnum {

	EXPIRES_IN(600),
	MESSAGE_EXPIRES_IN(600);
	
	@Getter private int value;
}
