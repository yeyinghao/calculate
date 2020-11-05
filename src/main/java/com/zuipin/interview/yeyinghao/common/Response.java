package com.zuipin.interview.yeyinghao.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zuipin.interview.yeyinghao.constant.CommonConstants;

import java.util.Objects;

/**
 * 通用返回对象，所有http接口都必须返回此对象
 *
 * @author yeyinghao
 */
@JsonInclude
public class Response<T> {

	/**
	 * 响应代码
	 */
	private Integer error_code;

	/**
	 * 响应消息
	 */
	private String error_message;

	/**
	 * 响应数据
	 */
	private T data;


	public static <T> Response<T> ok() {
		return restResult(null, CommonConstants.SUCCESS, "");
	}

	public static <T> Response<T> ok(String error_message) {
		return restResult(null, CommonConstants.SUCCESS, error_message);
	}

	public static <T> Response<T> ok(T data) {
		return restResult(data, CommonConstants.SUCCESS, "");
	}

	public static <T> Response<T> ok(T data, String error_message) {
		return restResult(data, CommonConstants.SUCCESS, error_message);
	}

	public static <T> Response<T> failed() {
		return restResult(null, CommonConstants.FAIL, "");
	}

	public static <T> Response<T> failed(String error_message) {
		return restResult(null, CommonConstants.FAIL, error_message);
	}

	public static <T> Response<T> failed(T data) {
		return restResult(data, CommonConstants.FAIL, "");
	}

	public static <T> Response<T> failed(T data, String error_message) {
		return restResult(data, CommonConstants.FAIL, error_message);
	}

	private static <T> Response<T> restResult(T data, int error_code, String error_message) {
		error_message = (error_message == null && error_code != 0) ? "业务异常" : error_message;
		return new Response<>(error_code, error_message, data);
	}

	/**
	 * 判断返回结果是否成功 code=0 为 true
	 *
	 * @return boolean
	 */
	public boolean success() {
		if (Objects.isNull(error_code)) {
			return true;
		}
		return !Objects.equals(0, error_code);
	}

	public Response() {
	}

	public Response(Integer error_code, String error_message, T data) {
		this.error_code = error_code;
		this.error_message = error_message;
		this.data = data;
	}

	public Integer getError_code() {
		return error_code;
	}

	public void setError_code(Integer error_code) {
		this.error_code = error_code;
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
