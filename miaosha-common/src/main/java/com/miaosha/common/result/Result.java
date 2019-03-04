package com.miaosha.common.result;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.miaosha.common.eum.ErrorEnum;
import org.springframework.data.domain.Page;

public class Result implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int FAILURE = 9999;
	
	public static Message getMessage(String msg, int code){
		return Message.builder()
				.code(code)
				.message(msg)
				.build();
	}
	
	public static Map<String, Object> getResultJson(int code){
		Message message = Result.getMessage(code);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("message", message.getMessage());
		
		return map;
	}

	public static Message getImageMessage(String data){
		Map<String, String> map = new HashMap<String, String>();
		map.put("path", data);
		
		ErrorEnum error = ErrorEnum.SUCCESS;
		
		return Message.builder()
				.code(error.getCode())
				.message(error.getMessage())
//				.data(RSAUtils.encrypt(JSON.toJSONString(map)))
				.data(data)
				.build();
	}
	
	public static Message getErrorMessage(int code, String errorMessage){
		return Message.builder()
				.code(code)
				.message(errorMessage)
				.build();
	}
	
	public static Message getErrorMessage(String errorMessage){
		return Message.builder()
				.code(FAILURE)
				.message(errorMessage)
				.build();
	}
	
	public static Message getSuccessMessage(){
		ErrorEnum error = ErrorEnum.SUCCESS;
		return Message.builder()
			.code(error.getCode())
			.message(error.getMessage())
			.build();
	}
	
	public static Message getMessage(Object data){
		if(null == data){
			return Message.builder().code(FAILURE).message("NULL").build();
		}
		
		ErrorEnum error = ErrorEnum.SUCCESS;
		return Message.builder()
				.code(error.getCode())
				.message(error.getMessage())
//				.data(RSAUtils.encrypt(JSON.toJSONString(data)))
				.data(data)
				.build();
	}

    public static Message getIllegalMessage(Object data){
        if(null == data){
            return Message.builder().code(FAILURE).message("NULL").build();
        }

        ErrorEnum error = ErrorEnum.SUCCESS;
        return Message.builder()
                .code(error.getCode())
                .message("恭喜您，没有违章")
//				.data(RSAUtils.encrypt(JSON.toJSONString(data)))
                .data(data)
                .build();
    }

	
	public static Message getMessage(ErrorEnum error){
		return Message.builder()
				.code(error.getCode())
				.message(error.getMessage())
				.build();
	}
	
	public static Message getMessage(Page<?> page){
		ErrorEnum error = ErrorEnum.SUCCESS;
		return Message.builder()
				.code(error.getCode())
				.message(error.getMessage())
//				.data(RSAUtils.encrypt(JSON.toJSONString(page.getContent())))
				.data(page.getContent())
				.page(new JsonPage(page))
				.build();
	}
	
	public static Message getMessage(Object data, Page<?> page) {
		ErrorEnum error = ErrorEnum.SUCCESS;
		return Message.builder()
				.code(error.getCode())
				.message(error.getMessage())
//				.data(RSAUtils.encrypt(JSON.toJSONString(data)))
				.data(data)
				.page(new JsonPage(page))
				.build();
	}
}
