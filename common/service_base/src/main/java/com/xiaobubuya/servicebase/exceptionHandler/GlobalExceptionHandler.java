package com.xiaobubuya.servicebase.exceptionHandler;

import com.xiaobubuya.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	//指定出现什么异常执行这个方法
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Result error(Exception e){
		e.printStackTrace();
		return Result.error().message("执行了全局异常处理...");
	}

	@ExceptionHandler(GuliException.class)
	@ResponseBody
	public Result error(GuliException e){
		log.error(ExceptionUtil.getMessage(e));
		e.printStackTrace();
		return Result.error().code(e.getCode()).message(e.getMsg());
	}
}