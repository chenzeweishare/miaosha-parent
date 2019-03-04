package com.miaosha.order.exception;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.alibaba.fastjson.JSONException;
import com.miaosha.common.eum.ErrorEnum;
import com.miaosha.common.exception.MiaoShaException;
import com.miaosha.common.result.Message;
import com.miaosha.common.result.Result;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public Message handleApiConstraintViolationException(ConstraintViolationException ex) {
        String message = "";  
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();  
        for (ConstraintViolation<?> violation : violations) {  
            message += violation.getMessage();
            log.info("Parameter error:" + violation.getPropertyPath() + ", " + violation.getMessageTemplate());
            break;
        }
        return Result.getErrorMessage(message);
    }
	
	@ResponseBody
    @ExceptionHandler(BindException.class)
	public Message handleParameValidException(BindException ex){
		BindingResult result = ex.getBindingResult();
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			if(null != list && !list.isEmpty()){
				ObjectError error = list.get(0);
				return Result.getErrorMessage(error.getDefaultMessage());
			}
		}
		
		return Result.getMessage(ErrorEnum.UNKNOW_ERROR);
	}
	
	@ResponseBody
	@ExceptionHandler(value = Exception.class)
    public Message handleException(Exception e){
        if(e instanceof ConstraintViolationException){
        	
        	ConstraintViolationException de = (ConstraintViolationException) e;
            return handleApiConstraintViolationException(de);
            
        }else if(e instanceof BindException){
        	
        	return handleParameValidException((BindException)e);
        	
        }else if(e instanceof MissingServletRequestParameterException){
        	
        	return Result.getMessage(ErrorEnum.PARAMETER_ERROR);
        	
        }else if(e instanceof MiaoShaException){

            MiaoShaException de = (MiaoShaException) e;
        	
        	if(null == de.getError()){
        		return Result.getErrorMessage(de.getMessage());
        	}else{
        		return Result.getMessage(de.getError());
        	}
        	
        }else if(e instanceof MethodArgumentTypeMismatchException){
        	
        	return Result.getMessage(ErrorEnum.NOT_FOUND_ERROR);
        	
        }else if(e instanceof JSONException){
        	return Result.getMessage(ErrorEnum.JSON_ERROR);
        }else if(e instanceof HttpRequestMethodNotSupportedException){
        	
        	return Result.getMessage(ErrorEnum.REQUEST_METHOD_ERROR);
        	
        }else if(e instanceof HttpMediaTypeNotAcceptableException){
        	
        	return Result.getMessage(ErrorEnum.HTTP_MEDIA_ERROR);
        }

        log.error("【系统异常】 {}", e);
        
        return Result.getMessage(ErrorEnum.UNKNOW_ERROR);
    }
}
