package com.zuipin.interview.yeyinghao.exception;

import com.zuipin.interview.yeyinghao.common.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 全局的的异常拦截器
 *
 * @author yeyh
 * @date 2018/05/22
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	private Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * 全局异常
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Response<Boolean> exception(Exception e) {
		log.info("全局异常, ex={}", e.getMessage(), e);
		return Response.failed(e.getMessage());
	}

	/**
	 * 业务异常
	 */
	@ExceptionHandler(ServiceException.class)
	@ResponseBody
	public Response<Boolean> serviceException(ServiceException e) {
		log.info("业务异常, ex={}", e.getMessage(), e);
		return Response.failed(e.getMessage());
	}

	/**
	 * 字段校验异常
	 */
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public Response<Boolean> methodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.info("字段校验异常, ex={}", e.getMessage(), e);
		List<FieldError> list = e.getBindingResult().getFieldErrors();
		return executeValidException(e, list);
	}

	/**
	 * 参数绑定异常
	 */
	@ExceptionHandler(value = BindException.class)
	public Response<Boolean> bindException(BindException e) {
		log.info("参数绑定异常, ex={}", e.getMessage(), e);
		List<FieldError> list = e.getBindingResult().getFieldErrors();
		return executeValidException(e, list);
	}

	private Response<Boolean> executeValidException(Exception e, List<FieldError> list) {
		//按需重新封装需要返回的错误信息
		StringBuilder sb = new StringBuilder();
		//解析原错误信息，封装后返回，此处返回非法的字段名称，错误信息
		list.forEach(
				item -> sb.append("入参字段").append(item.getField())
						.append(",").append(item.getDefaultMessage())
						.append(";")
		);
		return Response.failed(sb.toString());
	}
}
