package com.miaosha.common.exception;


import com.miaosha.common.eum.ErrorEnum;

public class MiaoShaException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8526150287200827219L;
	
	private ErrorEnum errorEnum;

    /**
     *  自定义错误信息
     * @param message
     */
    public MiaoShaException(String message) {
        super(message);
    }
    
    public MiaoShaException(ErrorEnum errorEnum){
    	super(errorEnum.getMessage());
    	this.errorEnum = errorEnum;
    }
    
    public ErrorEnum getError(){
    	return errorEnum;
    }
}
